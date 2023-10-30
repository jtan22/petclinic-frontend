package com.bw.petclinic.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CrashController {

    @GetMapping("/oops")
    public String error() {
        throw new RuntimeException("Expected Exception");
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

}
