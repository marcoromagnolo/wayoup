package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

/**
 * Created by Marco Romagnolo on 15/05/2015.
 */
public class AuthorizationException extends AbstractServiceException {

    public AuthorizationException(ErrorType errorType) {
        super(errorType);
    }
}
