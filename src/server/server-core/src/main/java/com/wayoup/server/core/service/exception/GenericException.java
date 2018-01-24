package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

import java.util.Map;

/**
 * Created by Marco on 30/07/2015.
 */
public class GenericException extends AbstractServiceException {

    public GenericException(ErrorType errorType) {
        super(errorType);
    }

    public GenericException(ErrorType errorType, Map<String, ErrorType> fields) {
        super(errorType, fields);
    }

}
