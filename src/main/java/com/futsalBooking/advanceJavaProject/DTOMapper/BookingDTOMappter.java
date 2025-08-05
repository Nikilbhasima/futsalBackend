package com.futsalBooking.advanceJavaProject.DTOMapper;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Bookings;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookingDTOMappter {


    public  List<BookingDTO> getBookingDTOs(List<Futsal_Booking> bookings) {
        BookingDTOMappter bookingDTOMappter=new BookingDTOMappter();
        List<BookingDTO> bookingDTOs = new ArrayList<>();
        for (Futsal_Booking data: bookings) {
            bookingDTOs.add(bookingDTOMappter.getBookingDTO(data));
        }
        return  bookingDTOs;
    }

    public  BookingDTO getBookingDTO(Futsal_Booking bookingDTO) {
        BookingDTO bookingDTO1 = new BookingDTO();
        bookingDTO1.setId(bookingDTO.getId());
        bookingDTO1.setPlaying_date(bookingDTO.getPlaying_date());
        bookingDTO1.setStarting_time(bookingDTO.getStarting_time());
        bookingDTO1.setBooking_date(bookingDTO.getBooking_date());
        bookingDTO1.setStatus(bookingDTO.getStatus());
        bookingDTO1.setEnding_time(bookingDTO.getEnding_time());

        return bookingDTO1;
    }
}
