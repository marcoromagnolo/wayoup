package com.wayoup.server.core.controller.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Marco Romagnolo on 26/03/2015.
 */
public class HttpNotFoundException extends HttpException {

    public HttpNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

}
