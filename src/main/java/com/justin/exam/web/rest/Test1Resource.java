package com.justin.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.justin.exam.domain.Test1;
import com.justin.exam.repository.Test1Repository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Test1.
 */
@RestController
@RequestMapping("/api")
public class Test1Resource {

    private final Logger log = LoggerFactory.getLogger(Test1Resource.class);

    @Inject
    private Test1Repository test1Repository;

    /**
     * POST  /test1s -> Create a new test1.
     */
    @RequestMapping(value = "/test1s",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody Test1 test1) throws URISyntaxException {
        log.debug("REST request to save Test1 : {}", test1);
        if (test1.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new test1 cannot already have an ID").build();
        }
        test1Repository.save(test1);
        return ResponseEntity.created(new URI("/api/test1s/" + test1.getId())).build();
    }

    /**
     * PUT  /test1s -> Updates an existing test1.
     */
    @RequestMapping(value = "/test1s",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody Test1 test1) throws URISyntaxException {
        log.debug("REST request to update Test1 : {}", test1);
        if (test1.getId() == null) {
            return create(test1);
        }
        test1Repository.save(test1);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /test1s -> get all the test1s.
     */
    @RequestMapping(value = "/test1s",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Test1>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Test1> page = test1Repository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/test1s", offset, limit);
        return new ResponseEntity<List<Test1>>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /test1s/:id -> get the "id" test1.
     */
    @RequestMapping(value = "/test1s/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Test1> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Test1 : {}", id);
        Test1 test1 = test1Repository.findOne(id);
        if (test1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(test1, HttpStatus.OK);
    }

    /**
     * DELETE  /test1s/:id -> delete the "id" test1.
     */
    @RequestMapping(value = "/test1s/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test1 : {}", id);
        test1Repository.delete(id);
    }
}
