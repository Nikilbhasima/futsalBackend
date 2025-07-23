package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface FutsalBookingServiceeRepository extends JpaRepository<Futsal_Booking,Integer> {
}
