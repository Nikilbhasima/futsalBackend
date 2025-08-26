package com.futsalBooking.advanceJavaProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String productCode;
    private String productName;
    private double totalAmount;
    private double taxAmount=0;
    private double serviceCharge=0;
    private double deliveryCharge=0;
    private String customerEmail;
    private String customerPhone;
}
