package com.futsalBooking.advanceJavaProject.controller.bookingController;

import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.service.FutsalBookingServiceImplementation;
import jakarta.websocket.server.PathParam;
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
        System.out.println(futsal_Booking.getBookingType());
        if(futsal_Booking.getBookingType().equals("book")){
            BookingDTO bookingDTO = futsalBookingServiceImplementation.bookFutsal(authentication,futsal_Booking,groundId);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
        }
        if(futsal_Booking.getBookingType().equals("challenge")){
            BookingDTO bookingDTO = futsalBookingServiceImplementation.bookFutsal(authentication,futsal_Booking,groundId);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @GetMapping("/getFutsalSlot/{slotId}/{bookingDate}")
    public ResponseEntity<List<BookingDTO>> getSlot(@PathVariable("slotId") int slotId, @PathVariable("bookingDate") LocalDate bookingDate){

        List<BookingDTO> bookingDTOS= futsalBookingServiceImplementation.findBookingsByGroundId(slotId,bookingDate);
//        if (bookingDTOS.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
        return ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }

    @GetMapping("/getBookingsByUserId/{bookingType}")
    public ResponseEntity<List<BookingDTO>>  getBookingsByUserId(Authentication authentication, @PathVariable("bookingType") String bookingType){
    List<BookingDTO> bookingDTOS=futsalBookingServiceImplementation.bookingByUserId(authentication,bookingType);
    return ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }

    @DeleteMapping("/cancalFutsalBooking/{groundId}")
    public ResponseEntity<String> cancelFutsalBooking(@PathVariable("groundId") int groundId) {
        if (futsalBookingServiceImplementation.cancelFutsalBooking(groundId)
        ) {
            return ResponseEntity.status(HttpStatus.OK).body("cancelled");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking not found");
    }

    @GetMapping("/getListOfFutsalChallenge")
    public ResponseEntity<List<BookingDTO>> getListOfFutsalChallenge(){
        List<BookingDTO> bookingDTOS= futsalBookingServiceImplementation.getListOfFutsalChallenge();

        return  ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }

    @PostMapping("acceptChallenge/{bookingId}")
    public ResponseEntity<BookingDTO> acceptChallenge(Authentication authentication, @PathVariable("bookingId") int bookingId){
        BookingDTO bookingDTO= futsalBookingServiceImplementation.acceptChallenge(authentication,bookingId);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }

    @GetMapping("/getMyChallenge")
    public ResponseEntity<List<BookingDTO>> getMyChallenge(Authentication authentication){
        List<BookingDTO> bookingDTOS=futsalBookingServiceImplementation.getMyFutsalChallenge(authentication);
        return  ResponseEntity.status(HttpStatus.OK).body(bookingDTOS);
    }

    @DeleteMapping("/cancelFutsalChallenge/{challengeId}")
    public ResponseEntity<String> cancelFutsalChallenge(Authentication authentication,@PathVariable("challengeId") int challengeId){
        boolean value= futsalBookingServiceImplementation.cancelFutsalChallenge(authentication,challengeId);
        if(value) {
            return ResponseEntity.status(HttpStatus.OK).body("cancelled");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed");
        }

    }

}
