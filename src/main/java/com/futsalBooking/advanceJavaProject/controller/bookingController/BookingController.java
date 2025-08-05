package com.futsalBooking.advanceJavaProject.controller.bookingController;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.service.FutsalBookingServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private FutsalBookingServiceImplementation futsalBookingServiceImplementation;


    @PostMapping("/bookFutsal/{groundId}")
    public ResponseEntity<BookingDTO> bookFutsal(Authentication authentication, @RequestBody Futsal_Booking futsal_Booking, @PathVariable("groundId") int groundId) {
         BookingDTO bookingDTO = futsalBookingServiceImplementation.bookFutsal(authentication,futsal_Booking,groundId);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);

    }

    @GetMapping("/getFutsalSlot/{slotId}/{bookingDate}")
    public ResponseEntity<List<BookingDTO>> getSlot(@PathVariable("slotId") int slotId, @PathVariable("bookingDate") LocalDate bookingDate){
        System.out.println("get futsal id "+slotId);
        System.out.println("get futsal id "+bookingDate);
        List<BookingDTO> bookingDTOS= futsalBookingServiceImplementation.findBookingsByGroundId(slotId,bookingDate);
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }
}
