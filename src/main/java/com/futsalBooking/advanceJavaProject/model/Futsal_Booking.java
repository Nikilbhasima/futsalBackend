package com.futsalBooking.advanceJavaProject.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String playing_date;
    private String playing_time;
    private String booking_date;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ground_id")
    private Futsal_Ground futsal_ground;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "challenger_id")
    private Users challenger_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "oppponent_id")
    private Users opponent_id;

}
