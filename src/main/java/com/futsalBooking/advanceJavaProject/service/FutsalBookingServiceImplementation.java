package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.DTOMapper.BookingDTOMappter;
import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalBookingServiceeRepository;
import com.futsalBooking.advanceJavaProject.repository.FutsalGroundServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.FutsalServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class FutsalBookingServiceImplementation implements FutsalBooking {

    @Autowired
    private FutsalBookingServiceeRepository futsalBookingServiceeRepository;

    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Autowired
    private FutsalGroundServiceRepository futsalGroundServiceRepository;

    @Autowired
    private BookingDTOMappter bookingDTOMappter;

    public BookingDTO bookFutsal(Authentication authentication, Futsal_Booking futsal_Booking, int groundId) {
        Users user=usersServiceRepository.findByPhoneNumber(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        Futsal_Ground futsal_ground =futsalGroundServiceRepository.findById(groundId).orElseThrow(()-> new RuntimeException("Ground not found"));
        Futsal_Booking saveBooking=new Futsal_Booking();
        saveBooking.setBooking_date(LocalDate.now());
        saveBooking.setChallenger_id(user);
        saveBooking.setFutsal_ground(futsal_ground);
        saveBooking.setStatus("pending");
        saveBooking.setPlaying_date(futsal_Booking.getPlaying_date());
        saveBooking.setStarting_time(futsal_Booking.getStarting_time());
        saveBooking.setEnding_time(futsal_Booking.getEnding_time());
        Futsal_Booking savedBooking=futsalBookingServiceeRepository.save(saveBooking);
      return bookingDTOMappter.getBookingDTO(savedBooking);
    }


    public List<BookingDTO> findBookingsByGroundId(int groundId, LocalDate playingDate) {
        System.out.println("get futsal id "+groundId+":"+playingDate);
        List<Futsal_Booking> futsalBooking= futsalBookingServiceeRepository.findBookingsByGroundId(groundId,playingDate);
        return bookingDTOMappter.getBookingDTOs(futsalBooking);
    }
}
