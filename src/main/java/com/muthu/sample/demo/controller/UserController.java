package com.muthu.sample.demo.controller;

import com.muthu.sample.demo.data.model.User;
import com.muthu.sample.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping(path = "/user")
public class UserController {


    @Autowired
    public UserService userService;

    @GetMapping("/")
    public ResponseEntity getCountyCode() {
        userService.add(new User());
        return ResponseEntity.ok().body("Hi user");
    }
}
