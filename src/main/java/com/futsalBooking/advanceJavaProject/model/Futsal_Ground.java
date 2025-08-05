package com.futsalBooking.advanceJavaProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Futsal_Ground {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String groundType;
    private float pricePerHour;
    private String image;

    @ManyToOne
    @JoinColumn(name = "futsal_Id")
    private Futsal futsal;

    @OneToMany(mappedBy = "futsal_ground",fetch = FetchType.EAGER)
    private List<Futsal_Booking> futsalBookings;



}
