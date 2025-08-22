package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.DTOMapper.FutsalDTOMapper;
import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FutsalServiceImplementation implements FutsalService {

    @Autowired
    private FutsalServiceRepository futsalServiceRepository;

    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @Autowired
    private FutsalDTOMapper futsalDTOMapper;

    @Override
    public FutsalDto addFutsal(Futsal futsal, Authentication authentication) {


        Futsal newFutsal = new Futsal();
        newFutsal.setFutsalName(futsal.getFutsalName());
        newFutsal.setFutsalAddress(futsal.getFutsalAddress());
        newFutsal.setDescription(futsal.getDescription());
        newFutsal.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        newFutsal.setFutsalClosingHours(futsal.getFutsalClosingHours());
        newFutsal.setFutsalLogo(futsal.getFutsalLogo());

        Users user = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();


        Futsal savedFutsal = futsalServiceRepository.save(newFutsal);
        FutsalDto futsalDto=futsalDTOMapper.mapFutsalDto(savedFutsal);
        user.setFutsal(savedFutsal);
        usersServiceRepository.save(user);

        return futsalDto;
    }

    @Override
    public FutsalDto getFutsal(Authentication authentication) {
        Users user = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow();
        if (user.getFutsal()!=null){
            return futsalDTOMapper.mapFutsalDto(user.getFutsal());

        }
        return  null;
    }

    @Override
    public FutsalDto getFutsalById(int id) {
        Futsal futsal=futsalServiceRepository.findById(id).orElseThrow();
        return futsalDTOMapper.mapFutsalDto(futsal);
    }



    @Override
    public List<FutsalDto> getAllFutsalList() {
        List<Futsal> futsalList = futsalServiceRepository.findAll();

        return futsalDTOMapper.getFutsalDtoList(futsalList);
    }

    @Override
    public FutsalDto editFutsalDetail(Futsal futsal, Authentication authentication) {
        Users users = usersServiceRepository.findByPhoneNumber(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found with phone: " + authentication.getName()));

        Futsal newFutsal = Optional.ofNullable(users.getFutsal())
                .orElseThrow(() -> new RuntimeException("Futsal not found for this user"));

        if (futsal.getFutsalName() != null) {
            newFutsal.setFutsalName(futsal.getFutsalName());
        }
        if (futsal.getFutsalAddress() != null) {
            newFutsal.setFutsalAddress(futsal.getFutsalAddress());
        }
        if (futsal.getDescription() != null) {
            newFutsal.setDescription(futsal.getDescription());
        }
        if (futsal.getFutsalLogo() != null) {
            newFutsal.setFutsalLogo(futsal.getFutsalLogo());
        }
        if (futsal.getFutsalOpeningHours() != null) {
            newFutsal.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        }
        if (futsal.getFutsalClosingHours() != null) {
            newFutsal.setFutsalClosingHours(futsal.getFutsalClosingHours());
        }

        newFutsal = futsalServiceRepository.save(newFutsal);
        return futsalDTOMapper.getFutsalDto(newFutsal);
    }


}
