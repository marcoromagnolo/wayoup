package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

import java.util.Map;

/**
 * Created by Marco Romagnolo on 28/03/2015.
 */
abstract class AbstractServiceException extends Exception {

    ErrorType type;

    private Map<String, ErrorType> fields;

    protected AbstractServiceException(ErrorType errorType) {
        this.type = errorType;
    }

    protected AbstractServiceException(ErrorType errorType, Map<String, ErrorType> fields) {
        this(errorType);
        this.fields = fields;
    }

    public ErrorType getType() {
        return type;
    }

    public Map<String, ErrorType> getFields() {
        return fields;
    }

}
