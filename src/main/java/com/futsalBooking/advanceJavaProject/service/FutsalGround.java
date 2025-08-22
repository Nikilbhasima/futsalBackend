package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FutsalGround {
    public FutsalGroundDTO add(Futsal_Ground futsal_Ground, Authentication authentication);

    public List<FutsalGroundDTO> getFutsalGroundList(Authentication authentication);

    public FutsalGroundDTO editGroundDetail(Futsal_Ground futsal_Ground,Authentication authentication);
}
