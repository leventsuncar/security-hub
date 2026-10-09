package com.lsuncar.securityhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/")
public class TestController {

    @GetMapping( "hello" )
    public String test() {
        return "Hello World";
    }


}
