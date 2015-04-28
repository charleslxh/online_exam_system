package com.online.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.online.exam.domain.Question;
import com.online.exam.repository.QuestionRepository;
import com.online.exam.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Question.
 */
@RestController
@RequestMapping("/api")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    @Inject
    private QuestionRepository questionRepository;

    /**
     * POST  /questions -> Create a new question.
     */
    @RequestMapping(value = "/questions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to save Question : {}", question);
        if (question.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new question cannot already have an ID").build();
        }
        questionRepository.save(question);
        return ResponseEntity.created(new URI("/api/questions/" + question.getId())).build();
    }

    /**
     * PUT  /questions -> Updates an existing question.
     */
    @RequestMapping(value = "/questions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to update Question : {}", question);
        if (question.getId() == null) {
            return create(question);
        }
        questionRepository.save(question);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /questions -> get all the questions.
     */
    @RequestMapping(value = "/questions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Question>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Question> page = questionRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/questions", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /questions/:id -> get the "id" question.
     */
    @RequestMapping(value = "/questions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Question> get(@PathVariable Long id) {
        log.debug("REST request to get Question : {}", id);
        return Optional.ofNullable(questionRepository.findOne(id))
            .map(question -> new ResponseEntity<>(
                question,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /questions/:id -> delete the "id" question.
     */
    @RequestMapping(value = "/questions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Question : {}", id);
        questionRepository.delete(id);
    }
}
