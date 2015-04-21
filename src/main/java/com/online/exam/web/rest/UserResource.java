package com.online.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.online.exam.domain.User;
import com.online.exam.web.rest.dto.UserDTO;
import com.online.exam.repository.AuthorityRepository;
import com.online.exam.repository.UserRepository;
import com.online.exam.security.AuthoritiesConstants;
import com.online.exam.security.SecurityUtils;
import com.online.exam.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.exam.web.rest.util.BasicUtil;
import com.online.exam.web.rest.util.PaginationUtil;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;

import java.util.List;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Supplier;
/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;
    
    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * POST  /users -> Create a new user.
     */
    @RequestMapping(value = "/users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid  User user) throws URISyntaxException {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new User cannot already have an ID").build();
        }
        userRepository.save(user);
        return ResponseEntity.created(new URI("/api/users/" + user.getLogin())).build();
    }

    /**
     * PUT  /users -> Updates an existing user.
     */
    @RequestMapping(value = "/users",
    		method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> update(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to update user : {}", user);
        if (user.getId() == null) {
           return create(user);
        };
        return userRepository
                .findOneByEmail(user.getEmail())
                .filter(u -> u.getLogin().equals(user.getLogin()))
                .map(u -> {
                    userService.updateOtherUserInformation(
                    	user.getLogin(),
                    	user.getPhone(),
                    	user.getGender(),
                    	user.getAge(),
                    	user.getClasses(),
                    	user.getDescription(),
                    	user.getAvatarUrl(),
                    	user.getFirstName(),
                    	user.getLastName(),
                    	user.getEmail()
                    );
                    return new ResponseEntity<String>(HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

	/**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<User> getAll() {
        log.debug("REST request to get all Users");
        return userRepository.findAllByDeleted();
    }

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/users/{login}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return userRepository.findOneByLogin(login)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /users/:id -> delete the "id" user.
     */
    @RequestMapping(value = "/users/delete",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@RequestParam(value = "id") Long id) {
        log.debug("REST request to delete user : {}", id);
        userService.deleteUserByAdmin(id);
    }
}
