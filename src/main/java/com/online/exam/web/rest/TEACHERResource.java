package com.online.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.online.exam.domain.TEACHER;
import com.online.exam.repository.TEACHERRepository;
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
 * REST controller for managing TEACHER.
 */
@RestController
@RequestMapping("/api")
public class TEACHERResource {

    private final Logger log = LoggerFactory.getLogger(TEACHERResource.class);

    @Inject
    private TEACHERRepository tEACHERRepository;

    /**
     * POST  /tEACHERs -> Create a new tEACHER.
     */
    @RequestMapping(value = "/tEACHERs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody TEACHER tEACHER) throws URISyntaxException {
        log.debug("REST request to save TEACHER : {}", tEACHER);
        if (tEACHER.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tEACHER cannot already have an ID").build();
        }
        tEACHERRepository.save(tEACHER);
        return ResponseEntity.created(new URI("/api/tEACHERs/" + tEACHER.getId())).build();
    }

    /**
     * PUT  /tEACHERs -> Updates an existing tEACHER.
     */
    @RequestMapping(value = "/tEACHERs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody TEACHER tEACHER) throws URISyntaxException {
        log.debug("REST request to update TEACHER : {}", tEACHER);
        if (tEACHER.getId() == null) {
            return create(tEACHER);
        }
        tEACHERRepository.save(tEACHER);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /tEACHERs -> get all the tEACHERs.
     */
    @RequestMapping(value = "/tEACHERs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TEACHER>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<TEACHER> page = tEACHERRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tEACHERs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tEACHERs/:id -> get the "id" tEACHER.
     */
    @RequestMapping(value = "/tEACHERs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TEACHER> get(@PathVariable Long id) {
        log.debug("REST request to get TEACHER : {}", id);
        return Optional.ofNullable(tEACHERRepository.findOne(id))
            .map(tEACHER -> new ResponseEntity<>(
                tEACHER,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tEACHERs/:id -> delete the "id" tEACHER.
     */
    @RequestMapping(value = "/tEACHERs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete TEACHER : {}", id);
        tEACHERRepository.delete(id);
    }
}
