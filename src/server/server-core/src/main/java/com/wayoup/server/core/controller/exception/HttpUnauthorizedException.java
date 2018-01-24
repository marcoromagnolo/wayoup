package com.wayoup.server.core.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Marco Romagnolo on 27/03/2015.
 */
public class HttpUnauthorizedException extends HttpException {

    public HttpUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
