package com.futsalBooking.advanceJavaProject.DTOMapper;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingDTOMappter {

    @Autowired
  private UserDTOMappter userDTOMappter;



    public List<BookingDTO> getBookingDTOs(List<Futsal_Booking> bookings) {
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        for (Futsal_Booking booking : bookings) {
            bookingDTOs.add(this.getBookingDTO(booking));
        }
        return bookingDTOs;
    }

    public  BookingDTO getBookingDTO(Futsal_Booking bookingDTO) {
        BookingDTO bookingDTO1 = new BookingDTO();
        bookingDTO1.setId(bookingDTO.getId());
        bookingDTO1.setPlaying_date(bookingDTO.getPlaying_date());
        bookingDTO1.setStarting_time(bookingDTO.getStarting_time());
        bookingDTO1.setBooking_date(bookingDTO.getBooking_date());
        bookingDTO1.setStatus(bookingDTO.getStatus());
        bookingDTO1.setEnding_time(bookingDTO.getEnding_time());
        bookingDTO1.setStatus(bookingDTO.getStatus());
        bookingDTO1.setMatchPaymentType(bookingDTO.getMatchPaymentType());
        bookingDTO1.setContactForMatch(bookingDTO.getContactForMatch());
        // Add null check for challenger
        if (bookingDTO.getChallenger_id() != null) {
            bookingDTO1.setChallengerDto(userDTOMappter.getUserDTO(bookingDTO.getChallenger_id()));
        }

        // Add null check for opponent - This was causing the error
        if (bookingDTO.getOpponent_id() != null) {
            bookingDTO1.setOpponentDto(userDTOMappter.getUserDTO(bookingDTO.getOpponent_id()));
        }

        return bookingDTO1;
    }
}
