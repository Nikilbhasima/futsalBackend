package com.futsalBooking.advanceJavaProject.DTOMapper;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import com.futsalBooking.advanceJavaProject.repository.FutsalGroundServiceRepository;
import com.futsalBooking.advanceJavaProject.repository.FutsalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FutsalDTOMapper {

    @Autowired
    private FutsalServiceRepository futsalServiceRepository;

    @Autowired
    private FutsalGroundServiceRepository futsalGroundServiceRepository;

    @Autowired
    private FutsalGroundDTOMapper futsalGroundDTOMapper;

    public FutsalDto mapFutsalDto(Futsal futsal) {
        FutsalGroundDTOMapper futsalGroundDTOMapper = new FutsalGroundDTOMapper();
        List<Futsal_Ground> futsalGroundList=futsalGroundServiceRepository.findByFutsal_id(futsal.getId());

        FutsalDto futsalDto=new FutsalDto();

        futsalDto.setId(futsal.getId());
        futsalDto.setFutsalName(futsal.getFutsalName());
        futsalDto.setFutsalAddress(futsal.getFutsalAddress());
        futsalDto.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        futsalDto.setFutsalClosingHours(futsal.getFutsalClosingHours());
        futsalDto.setDescription(futsal.getDescription());
        futsalDto.setFutsalLogo(futsal.getFutsalLogo());


        if(futsal.getFutsalGroundList() != null && !futsal.getFutsalGroundList().isEmpty()){
            List<FutsalGroundDTO> futsalGroundDTOList = new ArrayList<>();
            for (Futsal_Ground futsalGround : futsal.getFutsalGroundList()) {
                FutsalGroundDTO futsalGroundDTO = futsalGroundDTOMapper.groundDTO(futsalGround);
                futsalGroundDTOList.add(futsalGroundDTO);  // add DTO here!
            }
            futsalDto.setFutsalGroundList(futsalGroundDTOList);  // set DTO list to futsalDto
        }

        return futsalDto;
    }

    public FutsalDto getFutsalDto(Futsal futsal) {
        FutsalDto futsalDto=new FutsalDto();
        futsalDto.setId(futsal.getId());
        futsalDto.setFutsalName(futsal.getFutsalName());
        futsalDto.setFutsalAddress(futsal.getFutsalAddress());
        futsalDto.setDescription(futsal.getDescription());
        futsalDto.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        futsalDto.setFutsalClosingHours(futsal.getFutsalClosingHours());
        return futsalDto;
    }


    public List<FutsalDto> getFutsalDtoList(List<Futsal> futsalList) {

        List<FutsalDto> futsalDtoList=new ArrayList<>();
        for(Futsal futsal:futsalList){
            FutsalDto futsalDto=getFutsalDto(futsal);
           List<Futsal_Ground> futsalGroundDTOList=futsalGroundServiceRepository.findByFutsal_id(futsal.getId());
           List<FutsalGroundDTO> futsalGroundDTOList2=new ArrayList<>();
           for (Futsal_Ground futsalGround : futsalGroundDTOList) {
               futsalGroundDTOList2.add(futsalGroundDTOMapper.groundDTO(futsalGround));
           }
           futsalDto.setFutsalGroundList(futsalGroundDTOList2);
            futsalDtoList.add(futsalDto);
        }

        return futsalDtoList;
    }


}
