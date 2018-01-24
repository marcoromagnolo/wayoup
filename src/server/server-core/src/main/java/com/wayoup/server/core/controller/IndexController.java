package com.wayoup.server.core.controller;

import com.wayoup.server.core.controller.exception.HttpNotFoundException;
import com.wayoup.server.core.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Marco on 23/06/2015.
 */
@RestController
public class IndexController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getIndex() {
        Map<String, Object> config = configService.getConfig();
        return new ResponseEntity<>(config, HttpStatus.OK);
    }

    @RequestMapping(value = "*")
    ResponseEntity notFound() {
        throw new HttpNotFoundException();
    }

}
