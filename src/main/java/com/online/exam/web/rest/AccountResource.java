package com.online.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.online.exam.domain.Authority;
import com.online.exam.domain.User;
import com.online.exam.repository.UserRepository;
import com.online.exam.security.SecurityUtils;
import com.online.exam.service.MailService;
import com.online.exam.service.UserService;
import com.online.exam.web.rest.dto.UserDTO;
import com.online.exam.web.rest.util.BasicUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    /**
     * POST  /register -> register the user.
     */
    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {

        return userRepository.findOneByLogin(userDTO.getLogin())
            .map(user -> new ResponseEntity<>("login already in use", HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByUserNo(userDTO.getUserNo())
                .map(user -> new ResponseEntity<>("student number already in use", HttpStatus.BAD_REQUEST))
                .orElseGet(() -> userRepository.findOneByEmail(userDTO.getEmail())
                    .map(user -> new ResponseEntity<>("e-mail address already in use", HttpStatus.BAD_REQUEST))
                    .orElseGet(() -> {
                        User user = userService.createUserInformation(
                        	userDTO.getLogin(),
                        	userDTO.getUserNo(),
                        	userDTO.getPassword(),
	                        userDTO.getFirstName(),
	                        userDTO.getLastName(),
	                        userDTO.getEmail().toLowerCase(),
	                        userDTO.getLangKey(),
	                        userDTO.getRoles(),
                            userDTO.getDeleted()
	                    );
                        String baseUrl = BasicUtil.getEmailBasicUrl(request);
                        mailService.sendActivationEmail(user, baseUrl);
                        return new ResponseEntity<>(HttpStatus.CREATED);
                    })
                )
        );
    }
    /**
     * GET  /activate -> activate the registered user.
     */
    @RequestMapping(value = "/activate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return Optional.ofNullable(userService.activateRegistration(key))
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate -> check if the user is authenticated, and return its login.
     */
    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account -> get the current user.
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(
                new UserDTO(
                    user.getLogin(),
                    user.getUserNo(),
                    null,
                    user.getGender(),
                    user.getAge(),
                    user.getPhone(),
                    user.getClasses(),
                    user.getAvatarUrl(),
                    user.getDescription(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getLangKey(),
                    user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toCollection(LinkedList::new))),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account -> update the current user information.
     */
    @RequestMapping(value = "/account",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO) {
        return userRepository
            .findOneByLogin(userDTO.getLogin())
            .filter(u -> u.getLogin().equals(SecurityUtils.getCurrentLogin()))
            .map(u -> {
                userService.updateUserInformation(
                	userDTO.getLogin(),
                	userDTO.getPhone(),
                	userDTO.getGender(),
                	userDTO.getAge(),
                	userDTO.getClasses(),
                	userDTO.getDescription(),
                	userDTO.getAvatarUrl(),
                	userDTO.getFirstName(),
                	userDTO.getLastName(),
                	userDTO.getEmail()
                );
                return new ResponseEntity<String>(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /change_password -> changes the current user's password
     */
    @RequestMapping(value = "/account/change_password",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(password) || password.length() < 5 || password.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        String baseUrl = BasicUtil.getEmailBasicUrl(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
    * POST /updatePasswordById -> update password by id
    **/
    @RequestMapping(value = "/account/updatePassword",
            method = RequestMethod.POST)
    @Timed
    public ResponseEntity<?> upadtePassword(@RequestBody String password) {
        if (StringUtils.isEmpty(password) || password.length() < 5 || password.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.upadtePasswordById(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
