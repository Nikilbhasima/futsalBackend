package com.futsalBooking.advanceJavaProject.controller.groundController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ground")
public class GroundController {

    @PostMapping("/addGround")
    public void addGround(){}

}
