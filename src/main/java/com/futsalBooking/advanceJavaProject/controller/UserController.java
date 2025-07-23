package com.futsalBooking.advanceJavaProject.controller;

import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImplementation userServiceImplementation;
    @GetMapping("/welcome")
    public String getWelcome() {
        return "Welcome to the advance Java Project!";
    }

    @GetMapping("/getUser")
    public Users getUsers(Authentication authentication) {
        return userServiceImplementation.getUsers(authentication);
    }
}
