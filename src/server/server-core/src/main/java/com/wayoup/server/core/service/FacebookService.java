package com.wayoup.server.core.service;

import com.wayoup.server.core.service.dto.LoginDTO;
import com.wayoup.server.core.service.exception.AuthorizationException;
import com.wayoup.server.core.service.exception.DbException;
import com.wayoup.server.core.service.exception.GenericException;
import com.wayoup.server.core.service.exception.InternalException;

/**
 * Created by Marco Romagnolo on 12/05/2015.
 */
public interface FacebookService {

    public String login() throws GenericException;

    LoginDTO callback(String code, String remoteAddress, String headers) throws GenericException, InternalException;

    public void logout(String extId, String token, String remoteAddress, String headers) throws GenericException, AuthorizationException, InternalException;

}
