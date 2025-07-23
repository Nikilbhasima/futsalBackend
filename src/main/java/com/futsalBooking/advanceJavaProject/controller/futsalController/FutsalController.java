package com.futsalBooking.advanceJavaProject.controller.futsalController;


import com.futsalBooking.advanceJavaProject.model.Futsal;
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
@RequestMapping("api/futsal")
public class FutsalController {

    @Autowired
    private FutsalServiceImplementation futsalServiceImplementation;

    @PostMapping("/addFutsal")
    public ResponseEntity<Futsal> addFutsal(@RequestBody Futsal futsal, Authentication authentication){

       Futsal savedFutsal= futsalServiceImplementation.addFutsal(futsal, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFutsal);
    }
}
