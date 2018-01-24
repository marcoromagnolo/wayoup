package com.wayoup.server.core.service;

import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.dto.RegistrationDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;

import java.util.Locale;

/**
 * Created by Marco Romagnolo on 21/03/2015.
 */
public interface AccountService {

    LoginDTO login(String username, String password, boolean expiry, String remoteAddress, String headers) throws InternalException, GenericException;

    void logout(String username, String token, String remoteAddress, String headers) throws AuthorizationException, InternalException, GenericException;

    void recovery(String email, Locale locale) throws InternalException, GenericException;

    void reset(String username, String tempPassword, String password, String remoteAddr, String headers) throws InternalException, GenericException;

    RegistrationDTO register(Locale locale, String firstName, String lastName, String email, String username, String password) throws InternalException, GenericException;

    void confirm(String email, String code) throws InternalException, GenericException;

    void verifyEmail(String email) throws InternalException, GenericException;

    void verifyUsername(String username) throws InternalException, GenericException;
}
