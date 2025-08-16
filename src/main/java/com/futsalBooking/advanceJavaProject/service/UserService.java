package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.UserDTO;
import com.futsalBooking.advanceJavaProject.model.Users;
import org.springframework.security.core.Authentication;

public interface UserService {
    public UserDTO getUsers(Authentication authentication);

    public boolean editUserDetails(Authentication authentication, Users users);
}
