package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FutsalGroundServiceRepository extends JpaRepository<Futsal_Ground,Integer> {
}
