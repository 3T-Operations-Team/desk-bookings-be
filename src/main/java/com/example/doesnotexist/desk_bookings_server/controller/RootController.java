package com.example.doesnotexist.desk_bookings_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    @GetMapping
    public String root() {
        return "This is OPS team's backend service, dockerized";
    }
}
