package com.futsalBooking.advanceJavaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private int id;
    private LocalDate playing_date;
    private LocalTime starting_time;
    private LocalTime ending_time;
    private LocalDate booking_date;
    private String status;
    private FutsalGroundDTO futsalGroundDTO;


}
