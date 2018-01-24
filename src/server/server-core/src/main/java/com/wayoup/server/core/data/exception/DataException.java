package com.wayoup.server.core.data.exception;

/**
 * Created by Marco on 24/07/2015.
 */
public class DataException extends Exception {

    protected DataException(String message) {
        super(message);
    }

    public DataException(Exception e) {
        super(e);
    }
}
