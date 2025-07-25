package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.DTOMapper.FutsalDTOMapper;
import com.futsalBooking.advanceJavaProject.DTOMapper.FutsalGroundDTOMapper;
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
        FutsalGroundDTOMapper mapper = new FutsalGroundDTOMapper();
        Optional<Users> users = usersServiceRepository.findByPhoneNumber(authentication.getName());

        if (users.isPresent()) {
            Users user = users.get();
            Futsal futsal = user.getFutsal();

            futsal_Ground.setFutsal(futsal);
            Futsal_Ground savedGround = futsalGroundServiceRepository.save(futsal_Ground);


            List<Futsal_Ground> existingGrounds = futsal.getFutsalGroundList();
            if (existingGrounds == null) {
                existingGrounds = new ArrayList<>();
            }
            existingGrounds.add(savedGround);
            futsal.setFutsalGroundList(existingGrounds);
            futsalServiceRepository.save(futsal);

            return mapper.convertToFutsalGroundDTO(savedGround,user.getFutsal());
        }

        return null;
    }

    @Override
    public List<FutsalGroundDTO> getFutsalGroundList(Authentication authentication) {
        List<FutsalGroundDTO> futsalGroundDTOList = new ArrayList<>();
        Users user = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();

        List<Futsal_Ground> futsalGroundList=futsalGroundServiceRepository.findByFutsal_id(user.getFutsal().getId());
        if (futsalGroundList != null) {
            for (Futsal_Ground futsalGround : futsalGroundList) {
                FutsalGroundDTOMapper mapper = new FutsalGroundDTOMapper();
                FutsalDTOMapper futsalDTOMapper = new FutsalDTOMapper();
                FutsalGroundDTO futsalGroundDTO=mapper.groundDTO(futsalGround);
                futsalGroundDTO.setFutsalDto(futsalDTOMapper.getFutsalDto(user.getFutsal()));
                futsalGroundDTOList.add(futsalGroundDTO);
            }
        }

        return futsalGroundDTOList;
    }


}
