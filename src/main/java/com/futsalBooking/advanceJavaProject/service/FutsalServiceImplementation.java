package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FutsalServiceImplementation implements FutsalService {

    @Autowired
    private FutsalServiceRepository futsalServiceRepository;

    @Autowired
    private UsersServiceRepository usersServiceRepository;



    @Override
    public Futsal addFutsal(Futsal futsal, Authentication authentication) {
        Futsal newFutsal = new Futsal();
        newFutsal.setFutsalName(futsal.getFutsalName());
        newFutsal.setFutsalAddress(futsal.getFutsalAddress());
        newFutsal.setDescription(futsal.getDescription());
        newFutsal.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        newFutsal.setFutsalClosingHours(futsal.getFutsalClosingHours());

        Users user = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();


        Futsal savedFutsal = futsalServiceRepository.save(newFutsal);

        user.setFutsal(savedFutsal);
        usersServiceRepository.save(user);

        return savedFutsal;
    }

}
