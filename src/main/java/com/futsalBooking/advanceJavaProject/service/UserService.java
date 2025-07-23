package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.model.Users;
import org.springframework.security.core.Authentication;

public interface UserService {
    public Users getUsers(Authentication authentication);
}
