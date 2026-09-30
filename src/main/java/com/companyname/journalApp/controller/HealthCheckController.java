package com.companyname.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {


    @GetMapping("/health-check")
//    http://localhost:8080/health-check
    public String healthCheck(){
        return "Ok";
    }
}
