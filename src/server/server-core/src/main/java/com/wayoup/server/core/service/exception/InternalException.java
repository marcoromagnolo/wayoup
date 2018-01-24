package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

/**
 * Created by Marco on 30/07/2015.
 */
public abstract class InternalException extends AbstractServiceException {

    protected InternalException(ErrorType errorType) {
        super(errorType);
    }
}
