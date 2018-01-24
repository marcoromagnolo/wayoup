package com.wayoup.server.core.util;

import com.wayoup.server.core.enums.ErrorType;
import com.wayoup.server.core.service.exception.GenericException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marco on 22/08/2015.
 */
public class MultiErrorProcessor {

    private static MultiErrorProcessor instance;
    private Map<String, ErrorType> fields;

    private MultiErrorProcessor() {
        fields = new HashMap<>();
    }

    private static MultiErrorProcessor getInstance() {
        if (instance==null) {
            instance = new MultiErrorProcessor();
        }
        return instance;
    }

    public static void add(String field, ErrorType errorType) {
        getInstance().fields.put(field, errorType);
    }

    public static void verify(ErrorType errorType) throws GenericException {
        if (instance!=null) {
            Map<String, ErrorType> fields = instance.fields;
            instance = null;
            throw new GenericException(errorType, fields);
        }
    }
}
