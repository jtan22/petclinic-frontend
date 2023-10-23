package com.bw.petclinic.frontend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrashController {

    private static final Logger LOG = LoggerFactory.getLogger(CrashController.class);

    @GetMapping("/oops")
    public String error() {
        LOG.info("Get /oops");
        throw new RuntimeException("Expected Exception");
    }

}
