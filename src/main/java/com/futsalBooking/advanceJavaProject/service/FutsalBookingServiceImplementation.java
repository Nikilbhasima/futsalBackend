package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.DTOMapper.BookingDTOMappter;
import com.futsalBooking.advanceJavaProject.DTOMapper.FutsalDTOMapper;
import com.futsalBooking.advanceJavaProject.DTOMapper.FutsalGroundDTOMapper;
import com.futsalBooking.advanceJavaProject.DTOMapper.UserDTOMappter;
import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.dto.UserDTO;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FutsalBookingServiceImplementation implements FutsalBooking {

    @Autowired
    private FutsalBookingServiceeRepository futsalBookingServiceeRepository;

    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Autowired
    private FutsalGroundServiceRepository futsalGroundServiceRepository;

    @Autowired
    private FutsalServiceRepository futsalServiceRepository;

    @Autowired
    private BookingDTOMappter bookingDTOMappter;

    @Autowired
    private FutsalGroundDTOMapper futsalGroundDTOMapper;

    @Autowired
    private FutsalDTOMapper futsalDTOMapper;

    @Autowired
    private UserDTOMappter userDTOMappter;

    @Autowired
    private EmailService emailService;

    public BookingDTO bookFutsal(Authentication authentication, Futsal_Booking futsal_Booking, int groundId) {
        Users user=usersServiceRepository.findByPhoneNumber(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        Futsal_Ground futsal_ground =futsalGroundServiceRepository.findById(groundId).orElseThrow(()-> new RuntimeException("Ground not found"));
        Futsal_Booking saveBooking=new Futsal_Booking();
        saveBooking.setBooking_date(LocalDate.now());
        saveBooking.setChallenger_id(user);
        saveBooking.setFutsal_ground(futsal_ground);
        saveBooking.setStatus("pending");
        saveBooking.setBookingType(futsal_Booking.getBookingType());
        saveBooking.setPlaying_date(futsal_Booking.getPlaying_date());
        saveBooking.setStarting_time(futsal_Booking.getStarting_time());
        saveBooking.setEnding_time(futsal_Booking.getEnding_time());
        saveBooking.setMatchPaymentType(futsal_Booking.getMatchPaymentType());
        saveBooking.setContactForMatch(futsal_Booking.getContactForMatch());
        Futsal_Booking savedBooking=futsalBookingServiceeRepository.save(saveBooking);

//        emailService.sendBookingConfirmationEmail(user.getEmail(), String.valueOf(futsal_Booking.getPlaying_date()));

        return bookingDTOMappter.getBookingDTO(savedBooking);
    }


    public List<BookingDTO> findBookingsByGroundId(int groundId, LocalDate playingDate) {
        LocalTime now = LocalTime.now().withNano(0);
        LocalDate today = LocalDate.now();

        List<Futsal_Booking> futsalBooking= futsalBookingServiceeRepository.findBookingsByGroundId(groundId,playingDate);

        List<Futsal_Booking> updatedBookings = new ArrayList<>();

        for (Futsal_Booking booking : futsalBooking) {
            if (booking.getPlaying_date().isEqual(today)) {
                LocalTime start = booking.getStarting_time();
                LocalTime end = booking.getEnding_time();

                if (now.isBefore(start)) {
                    booking.setStatus("pending");
                } else if (now.isAfter(start) && now.isBefore(end)) {
                    booking.setStatus("playing");
                } else if (now.isAfter(end)) {
                    booking.setStatus("completed");
                }
                futsalBookingServiceeRepository.save(booking);


            } else if (booking.getPlaying_date().isBefore(today)) {
                booking.setStatus("completed");
                futsalBookingServiceeRepository.save(booking);
            }
            updatedBookings.add(booking);
        }

        return bookingDTOMappter.getBookingDTOs(updatedBookings);
    }

    public List<BookingDTO> bookingByUserId(Authentication authentication, String bookingType) {
        Users user = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Futsal_Booking> futsalBookings = futsalBookingServiceeRepository.findByChallenger_id(user.getId(), bookingType);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now().withNano(0);
        List<BookingDTO> bookingDTOS = new ArrayList<>();

        for (Futsal_Booking booking : futsalBookings) {

            if(booking.getPlaying_date().isEqual(today)) {
                LocalTime start = booking.getStarting_time();
                LocalTime end = booking.getEnding_time();

                if (now.isBefore(start)) {
                    booking.setStatus("pending");

                } else if (now.isAfter(start) && now.isBefore(end)) {
                    booking.setStatus("playing");
                } else if (now.isAfter(end)) {
                    booking.setStatus("completed");
                }
                futsalBookingServiceeRepository.save(booking);
            } else if (booking.getPlaying_date().isBefore(today)) {
                booking.setStatus("completed");
                futsalBookingServiceeRepository.save(booking);
            }
            Futsal_Ground ground=futsalGroundServiceRepository.findById(booking.getFutsal_ground().getId()).orElseThrow(()-> new RuntimeException("Ground not found"));
            FutsalGroundDTO futsalGroundDTO=futsalGroundDTOMapper.groundDTO(ground);
            Futsal futsal=futsalServiceRepository.findById(ground.getFutsal().getId()).orElseThrow(()-> new RuntimeException("futsal not found"));
            FutsalDto futsalDto=futsalDTOMapper.getFutsalDto(futsal);
            futsalGroundDTO.setFutsalDto(futsalDto);
            BookingDTO dto = bookingDTOMappter.getBookingDTO(booking);
            dto.setFutsalGroundDTO(futsalGroundDTO);
            bookingDTOS.add(dto);
        }
        return bookingDTOS;
    }

    @Override
    public boolean cancelFutsalBooking(int groundId) {
        Optional<Futsal_Booking> bookingOpt = futsalBookingServiceeRepository.findById(groundId);
        if(bookingOpt.isEmpty()){
            return false;
        }
        Futsal_Booking booking = bookingOpt.get();
        booking.setStatus("cancelled");
        futsalBookingServiceeRepository.save(booking);
        return true;

    }

    @Override
    public List<BookingDTO> getListOfFutsalChallenge() {
        LocalDate today = LocalDate.now();
        List<Futsal_Booking> futsalBooking= futsalBookingServiceeRepository.findBookingByChallenge();

        return handleBookingDTOForChallenge(futsalBooking,today);
    }

    @Override
    public BookingDTO acceptChallenge(Authentication authentication, int bookindId) {
        System.out.println(("booking id "+bookindId));
        System.out.println("user phone number:"+authentication.getName());
        Users users=usersServiceRepository.findByPhoneNumber(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        Futsal_Booking futsal_booking=futsalBookingServiceeRepository.findById(bookindId).orElseThrow(()-> new RuntimeException("futsal booking not found"));
        futsal_booking.setOpponent_id(users);
        futsal_booking=futsalBookingServiceeRepository.save(futsal_booking);

        return bookingDTOMappter.getBookingDTO(futsal_booking);
    }

    @Override
    public List<BookingDTO> getMyFutsalChallenge(Authentication authentication) {
        Users users=usersServiceRepository.findByPhoneNumber(authentication.getName()).orElseThrow(()-> new RuntimeException("User not found"));
        List<Futsal_Booking> futsalBooking= futsalBookingServiceeRepository.findBookingByChallengerId(users.getId());
        List<Futsal_Booking> futsalBookingsByOpponent=futsalBookingServiceeRepository.findBookingByOpponent(users.getId());
        futsalBooking.addAll(futsalBookingsByOpponent);
        LocalDate today = LocalDate.now();

        return handleBookingDTOForChallenge(futsalBooking,today);
    }

    @Override
    public boolean cancelFutsalChallenge(Authentication authentication, int groundId) {
        String playerPhoneNumber = authentication.getName();

        Optional<Futsal_Booking> bookingOpt = futsalBookingServiceeRepository.findById(groundId);
        if (bookingOpt.isEmpty()) {
            return false;
        }

        Futsal_Booking booking = bookingOpt.get();

        // Only challenger can cancel
        if (booking.getChallenger_id().getPhoneNumber().equals(playerPhoneNumber)) {
            booking.setStatus("cancelled");
        }

        if(booking.getOpponent_id().getPhoneNumber().equals(playerPhoneNumber)) {
            booking.setOpponent_id(null);
        }

        futsalBookingServiceeRepository.save(booking);
        return true;
    }



    public  List<BookingDTO> handleBookingDTOForChallenge(List<Futsal_Booking> futsalBooking,LocalDate today){
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        for (Futsal_Booking data : futsalBooking) {
            if (today.isAfter(data.getPlaying_date())) {
                data.setStatus("completed");
                data = futsalBookingServiceeRepository.save(data);
            }
//            getting booking dto mapper
            BookingDTO bookingDTO=bookingDTOMappter.getBookingDTO(data);
//            get user dto mapper
            Users user=usersServiceRepository.findById(data.getChallenger_id().getId()).orElseThrow(()-> new RuntimeException("User not found"));
            UserDTO userDTO=userDTOMappter.getUserDTO(user);
//            getting futsal dto mapper
            Futsal futsal=futsalServiceRepository.findById(data.getFutsal_ground().getFutsal().getId()).orElseThrow(()-> new RuntimeException("futsal not found"));
            FutsalDto futsalDto=futsalDTOMapper.getFutsalDto(futsal);
//            get futsal ground dto mapper
            Futsal_Ground futsal_ground=futsalGroundServiceRepository.findById(data.getFutsal_ground().getId()).orElseThrow(()->new RuntimeException("ground not found"));
            FutsalGroundDTO futsalGroundDTO=futsalGroundDTOMapper.groundDTO(futsal_ground);
            futsalGroundDTO.setFutsalDto(futsalDto);
//            setting up booking dto mapper
            bookingDTO.setFutsalGroundDTO(futsalGroundDTO);
            bookingDTO.setChallengerDto(userDTO);
            bookingDTOS.add(bookingDTO);

        }
        return  bookingDTOS;
    }

}
