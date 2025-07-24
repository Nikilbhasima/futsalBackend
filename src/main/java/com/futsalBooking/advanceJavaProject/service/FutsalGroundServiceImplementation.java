package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalGroundServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.FutsalServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FutsalGroundServiceImplementation implements FutsalGround {

    @Autowired
    private UsersServiceRepository usersServiceRepository;
    @Autowired
    private FutsalGroundServiceRepository futsalGroundServiceRepository;
    @Autowired
    private FutsalServiceRepository futsalServiceRepository;


    @Override
    public FutsalGroundDTO add(Futsal_Ground futsal_Ground, Authentication authentication) {
        Optional<Users> users = usersServiceRepository.findByPhoneNumber(authentication.getName());
        FutsalDto futsalDto = new FutsalDto();
        FutsalGroundDTO futsalGroundDTO = new FutsalGroundDTO();
        if (users.isPresent()) {
            Users user = users.get();
            Futsal futsal = user.getFutsal();


            futsalDto.setId(futsal.getId());
            futsalDto.setFutsalName(futsal.getFutsalName());
            futsalDto.setFutsalAddress(futsal.getFutsalAddress());
            futsalDto.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
            futsalDto.setFutsalClosingHours(futsal.getFutsalClosingHours());
            futsalDto.setDescription(futsal.getDescription());

            futsal_Ground.setFutsal(futsal);
            Futsal_Ground savedGround = futsalGroundServiceRepository.save(futsal_Ground);


            futsalGroundDTO.setFutsalDto(futsalDto);
            futsalGroundDTO.setId(savedGround.getId());
            futsalGroundDTO.setPricePerHour(savedGround.getPricePerHour());
            futsalGroundDTO.setImage(savedGround.getImage());
            futsalGroundDTO.setGroundType(savedGround.getGroundType());

            List<Futsal_Ground> existingGrounds = futsal.getFutsalGroundList();
            if (existingGrounds == null) {
                existingGrounds = new ArrayList<>();
            }
            existingGrounds.add(savedGround);
            futsal.setFutsalGroundList(existingGrounds);
            futsalServiceRepository.save(futsal);

            return futsalGroundDTO;
        }

        return null;
    }

}
