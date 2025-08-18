package com.futsalBooking.advanceJavaProject.controller.resetPassword;

import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import com.futsalBooking.advanceJavaProject.service.EmailService;
import com.futsalBooking.advanceJavaProject.service.OptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opt")
public class OptController {

    @Autowired
    private OptService optService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersServiceRepository usersServiceRepository;

    @PostMapping("/generate/{email}")
    public String generateOtp(@PathVariable String email) {
        String otp = optService.generateOtp(email);
        emailService.sendOtp(email, otp);
        return "OTP sent to " + email;
    }

    @PostMapping("/validate/{email}/{otp}")
    public ResponseEntity<String>  validateOtp(@PathVariable("email") String email, @PathVariable("otp") String otp) {
        boolean isValid = optService.validateOtp(email, otp);
        return isValid ? ResponseEntity.status(HttpStatus.OK).body("OTP is valid")  : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP") ;
    }
}
