package com.futsalBooking.advanceJavaProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",joinColumns = {@JoinColumn(name = "user_id")},inverseJoinColumns = {
            @JoinColumn(name = "role_id")
    })
    private List<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="futsal_id",referencedColumnName = "id")
    private Futsal futsal;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Futsal_Booking> futsalBookings;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Bookings> bookings;


}
