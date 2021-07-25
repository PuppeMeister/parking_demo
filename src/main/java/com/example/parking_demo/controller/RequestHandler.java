package com.example.parking_demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RequestHandler {

    @PostMapping
    public void retrieveIncomingRequest(){

    }
}


