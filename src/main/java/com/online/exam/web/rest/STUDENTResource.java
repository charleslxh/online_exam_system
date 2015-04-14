package com.online.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.online.exam.domain.STUDENT;
import com.online.exam.repository.STUDENTRepository;
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
 * REST controller for managing STUDENT.
 */
@RestController
@RequestMapping("/api")
public class STUDENTResource {

    private final Logger log = LoggerFactory.getLogger(STUDENTResource.class);

    @Inject
    private STUDENTRepository sTUDENTRepository;

    /**
     * POST  /sTUDENTs -> Create a new sTUDENT.
     */
    @RequestMapping(value = "/sTUDENTs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody STUDENT sTUDENT) throws URISyntaxException {
        log.debug("REST request to save STUDENT : {}", sTUDENT);
        if (sTUDENT.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sTUDENT cannot already have an ID").build();
        }
        sTUDENTRepository.save(sTUDENT);
        return ResponseEntity.created(new URI("/api/sTUDENTs/" + sTUDENT.getId())).build();
    }

    /**
     * PUT  /sTUDENTs -> Updates an existing sTUDENT.
     */
    @RequestMapping(value = "/sTUDENTs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody STUDENT sTUDENT) throws URISyntaxException {
        log.debug("REST request to update STUDENT : {}", sTUDENT);
        if (sTUDENT.getId() == null) {
            return create(sTUDENT);
        }
        sTUDENTRepository.save(sTUDENT);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /sTUDENTs -> get all the sTUDENTs.
     */
    @RequestMapping(value = "/sTUDENTs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<STUDENT>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<STUDENT> page = sTUDENTRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sTUDENTs", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sTUDENTs/:id -> get the "id" sTUDENT.
     */
    @RequestMapping(value = "/sTUDENTs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<STUDENT> get(@PathVariable Long id) {
        log.debug("REST request to get STUDENT : {}", id);
        return Optional.ofNullable(sTUDENTRepository.findOne(id))
            .map(sTUDENT -> new ResponseEntity<>(
                sTUDENT,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sTUDENTs/:id -> delete the "id" sTUDENT.
     */
    @RequestMapping(value = "/sTUDENTs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete STUDENT : {}", id);
        sTUDENTRepository.delete(id);
    }
}
