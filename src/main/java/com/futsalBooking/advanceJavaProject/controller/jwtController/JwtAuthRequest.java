package com.futsalBooking.advanceJavaProject.controller.jwtController;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthRequest {
    private String emailOrMobile;
    private String password;
}
