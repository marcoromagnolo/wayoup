package com.wayoup.server.core.controller.exception;

import com.wayoup.server.core.enums.ErrorType;
import com.wayoup.server.core.service.exception.GenericException;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Created by Marco on 19/08/2015.
 */
public abstract class HttpException extends RuntimeException {

    private HttpStatus httpStatus;

    private ErrorType errorType;

    private Map<String,ErrorType> fields;

    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(HttpStatus httpStatus, ErrorType errorType) {
        this(httpStatus);
        this.errorType = errorType;
    }

    public HttpException(HttpStatus httpStatus, GenericException e) {
        this(httpStatus);
        this.errorType = e.getType();
        this.fields = e.getFields();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Map<String, ErrorType> getFields() {
        return fields;
    }

    public String getMessage() {
        return httpStatus.value() + " " + httpStatus.getReasonPhrase();
    }

}
