package com.codewave.employeeservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*This @RefreshScope annotation forcefully refreshes the updated configurations of the department service from
* git repository thorough config server*/
@RefreshScope
@RestController
public class MessageController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("message")
    public String message(){
        return message;
    }

}
