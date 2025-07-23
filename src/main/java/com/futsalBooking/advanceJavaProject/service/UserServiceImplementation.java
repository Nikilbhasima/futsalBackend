package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Override
    public Users getUsers(Authentication authentication) {
        return usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();
    }
}
