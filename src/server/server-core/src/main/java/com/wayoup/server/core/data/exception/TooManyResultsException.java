package com.wayoup.server.core.data.exception;

/**
 * Created by Marco on 24/07/2015.
 */
public class TooManyResultsException extends DataException {

    public TooManyResultsException() {
        super("Error while find data: founded too many results");
    }

}
