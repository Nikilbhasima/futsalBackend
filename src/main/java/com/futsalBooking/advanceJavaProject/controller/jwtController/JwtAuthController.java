package com.futsalBooking.advanceJavaProject.controller.jwtController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
    @RequestMapping("/api/v1/auth")
    public class JwtAuthController {

        @Autowired
        private JwtAuthService jwtAuthService;

        @PostMapping("/register")
        public ResponseEntity<?> register (@RequestBody AuthRegisterRequest request) {
            try {
                JwtAuthResponse response = jwtAuthService.register(request);
                Map<String, Object> success = new HashMap<>();
                success.put("message", "Registration successful");
                success.put("token", response.getToken());
                return ResponseEntity.ok(success);
            } catch (RuntimeException ex) {
                Map<String, String> error = new HashMap<>();
                error.put("message", ex.getMessage());
                return ResponseEntity.badRequest().body(error);
            }
        }

        @PostMapping("/login")
        public ResponseEntity<JwtAuthResponse> authenticate (@RequestBody JwtAuthRequest request) {
            System.out.println("data has arrived"+request.getPassword());
            System.out.println("data has arrived"+request.getEmailOrMobile());
            return ResponseEntity.ok(jwtAuthService.authenticate(request));
        }
}
