package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.model.Futsal;
import org.springframework.security.core.Authentication;

public interface FutsalService {

    public Futsal addFutsal(Futsal futsal, Authentication authentication);
}
