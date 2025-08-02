package com.futsalBooking.advanceJavaProject.controller.futsalController;


import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.service.FutsalServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/futsal")
public class FutsalController {

    @Autowired
    private FutsalServiceImplementation futsalServiceImplementation;

    @PostMapping("/addFutsal")
    public ResponseEntity<FutsalDto> addFutsal(@RequestBody Futsal futsal, Authentication authentication){

       FutsalDto futsalDto = futsalServiceImplementation.addFutsal(futsal, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(futsalDto);
    }

    @GetMapping("/getFutsal")
    public ResponseEntity<FutsalDto> getFutsal(Authentication authentication){

        FutsalDto futsalDto = futsalServiceImplementation.getFutsal(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(futsalDto);
    }

    @GetMapping("/getAllFutsal")
    public ResponseEntity<List<FutsalDto> > getAllFutsal(){
        List<FutsalDto> futsalDtoList = futsalServiceImplementation.getAllFutsalList();
        return ResponseEntity.ok(futsalDtoList);
    }
    @GetMapping("/getFutsalById/{futsalId}")
    public ResponseEntity<FutsalDto> getFutsal(@PathVariable("futsalId") int id){
        System.out.println("get futsal id "+id);
        FutsalDto futsalDto = futsalServiceImplementation.getFutsalById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(futsalDto);
    }

}
