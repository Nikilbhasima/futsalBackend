package com.futsalBooking.advanceJavaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String paymentUrl;
    private String transactionUuid;
    private String signature;
    private String status;
    private String message;
    private Map <String, String> formData;
}
