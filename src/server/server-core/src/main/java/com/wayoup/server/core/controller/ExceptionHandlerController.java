package com.wayoup.server.core.controller;

import com.wayoup.server.core.controller.exception.HttpException;
import com.wayoup.server.core.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.wayoup.server.core.enums.ErrorType.ERROR_GENERIC;

/**
 * Created by Marco Romagnolo on 26/03/2015.
 */
@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerController {

    private static final Logger logger = Logger.getLogger(ExceptionHandlerController.class.getName());

    private ResponseEntity toResponse(ErrorType errorType, Map<String, ErrorType> fields, HttpStatus httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("httpStatus", httpStatus.value());
        response.put("httpReason", httpStatus.getReasonPhrase());
        Map<String, Object> error = new HashMap<>();
        error.put("code", errorType.name());
        error.put("message", errorType.getMessage());
        response.put("error", error);
        if (fields!=null) {
            Map<String, String> errorFields = new HashMap<>();
            for (Map.Entry entry : fields.entrySet()) {
                errorFields.put(entry.getKey().toString(), entry.getValue().toString());
            }
            error.put("fields", errorFields);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        return toResponse(ERROR_GENERIC, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {HttpException.class})
    public ResponseEntity handleHttpException(HttpException ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        return toResponse(ex.getErrorType(), ex.getFields(), ex.getHttpStatus());
    }


}
