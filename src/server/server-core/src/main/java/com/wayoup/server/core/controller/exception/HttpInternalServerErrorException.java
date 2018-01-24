package com.wayoup.server.core.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Marco on 22/08/2015.
 */
public class HttpInternalServerErrorException extends HttpException {

    public HttpInternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
