package com.futsalBooking.advanceJavaProject.controller.jwtController;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRegisterRequest {
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private String password;


}
