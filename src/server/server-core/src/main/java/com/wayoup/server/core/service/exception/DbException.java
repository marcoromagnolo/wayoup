package com.wayoup.server.core.service.exception;

import com.wayoup.server.core.enums.ErrorType;

/**
 * Created by Marco on 30/07/2015.
 */
public class DbException extends InternalException {

    public DbException() {
        super(ErrorType.ERROR_DATABASE);
    }

}
