package com.futsalBooking.advanceJavaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<BookingDTO> bookingDTOS;

}
