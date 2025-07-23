package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Futsal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FutsalServiceRepository extends JpaRepository<Futsal,Integer> {
}
