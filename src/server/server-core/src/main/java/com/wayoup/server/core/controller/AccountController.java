package com.wayoup.server.core.controller;

import com.wayoup.server.core.controller.exception.HttpInternalServerErrorException;
import com.wayoup.server.core.controller.exception.HttpUnauthorizedException;
import com.wayoup.server.core.controller.exception.HttpForbiddenException;
import com.wayoup.server.core.controller.util.HttpUtil;
import com.wayoup.server.core.service.AccountService;
import com.wayoup.server.core.service.UserService;
import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.dto.UserDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;
import com.wayoup.server.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marco Romagnolo on 22/04/2015.
 */
@RestController
//@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResponseEntity getAccount(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "token") String token) {
        try {
            UserDTO user = userService.getUser(userId, token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity saveAccount(@RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "token") String token,
                                     @RequestParam(value = "firstName") String firstName,
                                     @RequestParam(value = "lastName") String lastName,
                                     @RequestParam(value = "email") String email,
                                     @RequestParam(value = "birthday") String birthStr,
                                     @RequestParam(value = "phone") String phone) {
        try {
            Date birthday = DateUtil.parseDate(birthStr);
            userService.saveUser(userId, token, firstName, lastName, email, birthday, phone);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/account/changepassword", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestParam(value = "userId") String userId,
                                      @RequestParam(value = "token") String token,
                                      @RequestParam(value = "password") String password) {
        try {
            userService.changePassword(userId, token, password);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    ResponseEntity login(@RequestParam("username") String username, @RequestParam("password") String password,
                         @RequestParam(value = "expiry", defaultValue = "false") boolean expiry,
                         HttpServletRequest request) {
        try {
            LoginDTO login = accountService.login(username, password, expiry, request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(login, HttpStatus.CREATED);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    ResponseEntity logout(@RequestParam("id") String id, @RequestParam("token") String token, HttpServletRequest request) {
        try {
            accountService.logout(id, token, request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthorizationException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpUnauthorizedException();
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/recovery", method = RequestMethod.POST)
    ResponseEntity recovery(@RequestParam(value = "email") String email,
                            @RequestParam(value = "lang", defaultValue = "en", required = false) String lang) {
        try {
            Locale locale = new Locale(lang);
            accountService.recovery(email, locale);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    ResponseEntity reset(@RequestParam("username") String username, @RequestParam("tempPassword") String tempPassword,
                         @RequestParam("password") String password,
                         HttpServletRequest request) {
        try {
            accountService.reset(username, tempPassword, password, request.getRemoteAddr(), HttpUtil.getHeaders(request));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseEntity register(@RequestBody Map<String, String> map) {
        try {
            Locale locale = new Locale(map.get("locale"));
            accountService.register(locale, map.get("firstName"), map.get("lastName"), map.get("email"), map.get("username"), map.get("password"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/register/verify/email", method = RequestMethod.POST)
    ResponseEntity verifyEmail(@RequestBody Map<String, String> map) {
        try {
            accountService.verifyEmail(map.get("email"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    ResponseEntity confirm(@RequestParam("email") String email, @RequestParam("code") String code) {
        try {
            accountService.confirm(email, code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    ResponseEntity confirmByLink(@RequestParam("email") String email, @RequestParam("code") String code) {
        try {
            accountService.confirm(email, code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpInternalServerErrorException();
        } catch (GenericException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new HttpForbiddenException(e);
        }
    }

}
