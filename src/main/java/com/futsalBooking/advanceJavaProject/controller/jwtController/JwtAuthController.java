package com.futsalBooking.advanceJavaProject.controller.jwtController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/auth")
    public class JwtAuthController {

        @Autowired
        private JwtAuthService jwtAuthService;

        @PostMapping("/register")
        public ResponseEntity<JwtAuthResponse> register (@RequestBody AuthRegisterRequest request) {
            return ResponseEntity.ok(jwtAuthService.register(request));
        }

        @PostMapping("/login")
        public ResponseEntity<JwtAuthResponse> authenticate (@RequestBody JwtAuthRequest request) {
            System.out.println(request.getPassword());
            System.out.println(request.getEmailOrMobile());
            return ResponseEntity.ok(jwtAuthService.authenticate(request));
        }
}
