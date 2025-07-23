    package com.futsalBooking.advanceJavaProject.model;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;

    @Entity
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Futsal {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String futsalName;
        private String futsalAddress;
        private String description;
        private String futsalOpeningHours;
        private String futsalClosingHours;


        @OneToMany(mappedBy = "futsal" , cascade = CascadeType.ALL)
        private List<Futsal_Ground> futsalGroundList ;


    }
