package com.futsalBooking.advanceJavaProject.dto;

import com.futsalBooking.advanceJavaProject.model.Futsal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FutsalGroundDTO {
    private int id;
    private String groundType;
    private float pricePerHour;
    private String image;
    private FutsalDto futsalDto;

}
