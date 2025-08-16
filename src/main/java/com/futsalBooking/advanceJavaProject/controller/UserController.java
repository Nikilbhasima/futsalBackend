package com.futsalBooking.advanceJavaProject.controller;

import com.futsalBooking.advanceJavaProject.dto.UserDTO;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserDTO> getUsers(Authentication authentication) {
        UserDTO userDTO=userServiceImplementation.getUsers(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/editUserDetails")
    public ResponseEntity<Boolean> editUserDetails(Authentication authentication,@RequestBody Users users) {
        boolean success = userServiceImplementation.editUserDetails(authentication, users);
        return  ResponseEntity.status(HttpStatus.OK).body(success);
    }
}
