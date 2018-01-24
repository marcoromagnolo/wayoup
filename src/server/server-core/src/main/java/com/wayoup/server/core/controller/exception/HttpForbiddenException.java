package com.wayoup.server.core.controller.exception;

import com.wayoup.server.core.service.exception.GenericException;
import org.springframework.http.HttpStatus;

/**
 * Created by Marco on 19/08/2015.
 */
public class HttpForbiddenException extends HttpException {

    public HttpForbiddenException(GenericException e) {
        super(HttpStatus.BAD_REQUEST, e);
    }

}
