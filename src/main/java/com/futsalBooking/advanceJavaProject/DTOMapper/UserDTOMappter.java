package com.futsalBooking.advanceJavaProject.DTOMapper;

import com.futsalBooking.advanceJavaProject.dto.UserDTO;
import com.futsalBooking.advanceJavaProject.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMappter {

    public UserDTO getUserDTO(Users users) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(users.getUsername());
        userDTO.setId(users.getId());
        userDTO.setEmail(users.getEmail());
        userDTO.setImage(users.getImage());
        userDTO.setPhoneNumber(users.getPhoneNumber());
        return userDTO;
    }
}
