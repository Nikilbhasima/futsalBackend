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
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String phoneNumber;
    private String image;
    private String address;
    private List<BookingDTO> bookings;
}
