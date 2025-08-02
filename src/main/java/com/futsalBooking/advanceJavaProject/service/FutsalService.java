package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FutsalService {

    public FutsalDto addFutsal(Futsal futsal, Authentication authentication);

    public FutsalDto getFutsal(Authentication authentication);

    public FutsalDto getFutsalById(int id);

    public List<FutsalDto> getAllFutsalList();
}
