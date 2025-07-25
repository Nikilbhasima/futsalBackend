package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import org.springframework.security.core.Authentication;

public interface FutsalService {

    public FutsalDto addFutsal(Futsal futsal, Authentication authentication);

    public FutsalDto getFutsal(Authentication authentication);
}
