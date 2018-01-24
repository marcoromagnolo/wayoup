package com.wayoup.server.core.controller.exception;

import com.wayoup.server.core.enums.ErrorType;
import org.springframework.http.HttpStatus;

/**
 * Created by Marco on 22/08/2015.
 */
public class HttpBadRequestException extends HttpException {

    public HttpBadRequestException(ErrorType errorType) {
        super(HttpStatus.FORBIDDEN, errorType);
    }

}
