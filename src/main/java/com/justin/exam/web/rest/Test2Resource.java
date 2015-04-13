package com.justin.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.justin.exam.domain.Test2;
import com.justin.exam.repository.Test2Repository;
import com.justin.exam.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Test2.
 */
@RestController
@RequestMapping("/api")
public class Test2Resource {

    private final Logger log = LoggerFactory.getLogger(Test2Resource.class);

    @Inject
    private Test2Repository test2Repository;

    /**
     * POST  /test2s -> Create a new test2.
     */
    @RequestMapping(value = "/test2s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Test2 test2) throws URISyntaxException {
        log.debug("REST request to save Test2 : {}", test2);
        if (test2.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test2 cannot already have an ID").build();
        }
        test2Repository.save(test2);
        return ResponseEntity.created(new URI("/api/test2s/" + test2.getId())).build();
    }

    /**
     * PUT  /test2s -> Updates an existing test2.
     */
    @RequestMapping(value = "/test2s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Test2 test2) throws URISyntaxException {
        log.debug("REST request to update Test2 : {}", test2);
        if (test2.getId() == null) {
            return create(test2);
        }
        test2Repository.save(test2);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test2s -> get all the test2s.
     */
    @RequestMapping(value = "/test2s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Test2>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Test2> page = test2Repository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test2s", offset, limit);
        return new ResponseEntity<List<Test2>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test2s/:id -> get the "id" test2.
     */
    @RequestMapping(value = "/test2s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test2> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Test2 : {}", id);
        Test2 test2 = test2Repository.findOne(id);
        if (test2 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(test2, HttpStatus.OK);
    }

    /**
     * DELETE  /test2s/:id -> delete the "id" test2.
     */
    @RequestMapping(value = "/test2s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test2 : {}", id);
        test2Repository.delete(id);
    }
}
