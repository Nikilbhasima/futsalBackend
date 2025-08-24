package com.futsalBooking.advanceJavaProject.model;

import com.futsalBooking.advanceJavaProject.enumFile.PaymentStatus;
import com.futsalBooking.advanceJavaProject.enumFile.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Futsal_Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate playing_date;
    private LocalTime starting_time;
    private LocalTime ending_time;
    private LocalDate booking_date;
    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentType matchPaymentType;
    private String contactForMatch;
    private String bookingType;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ground_id")
    private Futsal_Ground futsal_ground;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "challenger_id")
    private Users challenger_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "opponent_id")
    private Users opponent_id;


}
