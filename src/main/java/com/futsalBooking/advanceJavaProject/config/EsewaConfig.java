package com.futsalBooking.advanceJavaProject.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Setter
@Getter
public class EsewaConfig {
    public EsewaConfig(
            @Value("${esewa.merchant.code}") String merchantCode,
            @Value("${esewa.service.charge}") int serviceCharge,
            @Value("${esewa.delivery.charge}") int deliveryCharge,
            @Value("${esewa.tax.amount}") int taxAmount,
            @Value("${esewa.success.url}") String successUrl,
            @Value("${esewa.failure.url}") String failureUrl,
            @Value("${esewa.payment.url}") String paymentUrl,
            @Value("${esewa.verification.url}") String verificationUrl,
            @Value("${esewa.secret.key}") String secretKey
    ) {
        this.merchantCode = merchantCode;
        this.serviceCharge = serviceCharge;
        this.deliveryCharge = deliveryCharge;
        this.taxAmount = taxAmount;
        this.successUrl = successUrl;
        this.failureUrl = failureUrl;
        this.paymentUrl = paymentUrl;
        this.verificationUrl = verificationUrl;
        this.secretKey = secretKey;
    }
    private String merchantCode;
    private int serviceCharge;
    private int deliveryCharge;
    private int taxAmount;
    private String successUrl;
    private String failureUrl;
    private String paymentUrl;
    private String verificationUrl;
    private String secretKey;
}
