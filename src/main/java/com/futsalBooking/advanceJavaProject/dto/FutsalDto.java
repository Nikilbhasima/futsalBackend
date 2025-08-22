package com.futsalBooking.advanceJavaProject.dto;

import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FutsalDto {
    private int id;
    private String futsalName;
    private String futsalAddress;
    private String description;
    private String futsalOpeningHours;
    private String futsalClosingHours;
    private String futsalLogo;
    private Futsal_Ground futsalGround;
    private List<FutsalGroundDTO> futsalGroundList;
}
