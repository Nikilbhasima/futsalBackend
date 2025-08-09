package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

public interface FutsalBooking {

    public List<BookingDTO> findBookingsByGroundId(int groundId, LocalDate bookingDate);

    public BookingDTO bookFutsal(Authentication authentication, Futsal_Booking futsal_Booking, int groundId);

    public List<BookingDTO> bookingByUserId(Authentication authentication, String bookingType);

    public boolean cancelFutsalBooking(int groundId);
}
