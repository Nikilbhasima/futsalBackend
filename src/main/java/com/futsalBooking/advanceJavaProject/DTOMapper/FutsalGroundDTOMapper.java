package com.futsalBooking.advanceJavaProject.DTOMapper;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;

public class FutsalGroundDTOMapper {

    public FutsalGroundDTO convertToFutsalGroundDTO(Futsal_Ground ground, Futsal futsal) {
        FutsalGroundDTO futsalGroundDTO = new FutsalGroundDTO();

        FutsalDto futsalDto = new FutsalDto();

        futsalDto.setId(futsal.getId());
        futsalDto.setFutsalName(futsal.getFutsalName());
        futsalDto.setFutsalAddress(futsal.getFutsalAddress());
        futsalDto.setFutsalOpeningHours(futsal.getFutsalOpeningHours());
        futsalDto.setFutsalClosingHours(futsal.getFutsalClosingHours());
        futsalDto.setDescription(futsal.getDescription());

        futsalGroundDTO.setFutsalDto(futsalDto);
        futsalGroundDTO.setId(ground.getId());
        futsalGroundDTO.setPricePerHour(ground.getPricePerHour());
        futsalGroundDTO.setImage(ground.getImage());
        futsalGroundDTO.setGroundType(ground.getGroundType());

        return futsalGroundDTO;
    }

    public FutsalGroundDTO groundDTO(Futsal_Ground ground) {
        FutsalGroundDTO futsalGroundDTO = new FutsalGroundDTO();
        futsalGroundDTO.setId(ground.getId());
        futsalGroundDTO.setPricePerHour(ground.getPricePerHour());
        futsalGroundDTO.setImage(ground.getImage());
        futsalGroundDTO.setGroundType(ground.getGroundType());
        return futsalGroundDTO;
    }
}
