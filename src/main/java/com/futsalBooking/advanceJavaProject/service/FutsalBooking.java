package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;

import java.time.LocalDate;
import java.util.List;

public interface FutsalBooking {

    public List<BookingDTO> findBookingsByGroundId(int groundId, LocalDate bookingDate);
}
