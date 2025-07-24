package com.futsalBooking.advanceJavaProject.controller.groundController;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.dto.FutsalGroundDTO;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import com.futsalBooking.advanceJavaProject.service.FutsalGroundServiceImplementation;
import com.futsalBooking.advanceJavaProject.service.FutsalServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ground")
public class GroundController {

    @Autowired
    private FutsalGroundServiceImplementation futsalGroundServiceImplementation;


    @PostMapping("/addGround")
    public ResponseEntity<FutsalGroundDTO> addGround(Authentication authentication, @RequestBody Futsal_Ground futsal_Ground){
       FutsalGroundDTO futsalGroundDTO =futsalGroundServiceImplementation.add(futsal_Ground,authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(futsalGroundDTO);
    }

}
