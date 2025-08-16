package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.DTOMapper.BookingDTOMappter;
import com.futsalBooking.advanceJavaProject.DTOMapper.UserDTOMappter;
import com.futsalBooking.advanceJavaProject.dto.BookingDTO;
import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.UserDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalBookingServiceeRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Autowired
    private UserDTOMappter userDTOMappter;

    @Autowired
    private FutsalBookingServiceeRepository futsalBookingServiceeRepository;

    @Autowired
    private BookingDTOMappter bookingDTOMappter;

    @Override
    public UserDTO getUsers(Authentication authentication) {

        Users users=usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();
        UserDTO userDTO=userDTOMappter.getUserDTO(users);

        List<Futsal_Booking> futsalBooking= futsalBookingServiceeRepository.findBookingByChallengerId(users.getId());
        List<Futsal_Booking> futsalBookingsByOpponent=futsalBookingServiceeRepository.findBookingByOpponent(users.getId());
        futsalBooking.addAll(futsalBookingsByOpponent);
        List<BookingDTO> bookingDTOS=handleBookingDto(futsalBooking);
        userDTO.setBookings(bookingDTOS);
        return userDTO;
    }

    @Override
    public boolean editUserDetails(Authentication authentication, Users users) {
        System.out.println("image url"+users.getImage());
        System.out.println("user location:"+users.getAddress());
        Users users1 = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        users1.setUsername(users.getUsername());
        users1.setPhoneNumber(users.getPhoneNumber());
        users1.setEmail(users.getEmail());
        users1.setImage(users.getImage());
        users1.setAddress(users.getAddress());

        Users updatedUser = usersServiceRepository.save(users1);

        return updatedUser.equals(users1);
    }



    public List<BookingDTO> handleBookingDto(List<Futsal_Booking> futsalBooking) {
        List<BookingDTO> bookingDTOS=new ArrayList<>();
        for (Futsal_Booking futsalBookingDTO : futsalBooking) {
            BookingDTO bookingDTO=bookingDTOMappter.getBookingDTO(futsalBookingDTO);
            bookingDTOS.add(bookingDTO);
        }
        return bookingDTOS;
    }
}
