package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

/**
 * Created by Marco on 30/08/2015.
 */
public class NetworkException extends InternalException {

    protected NetworkException() {
        super(ErrorType.ERROR_NETWORK);
    }
}
