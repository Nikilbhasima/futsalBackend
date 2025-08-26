package com.futsalBooking.advanceJavaProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionUuid;
    private String productCode;
    private String productName;
    private Double totalAmount;
    private Double taxAmount;
    private Double serviceCharge;
    private Double deliveryCharge;
    private String status;
    private String customerEmail;
    private String esewaTransactionCode;
    private String signature;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Futsal_Booking booking;
}
