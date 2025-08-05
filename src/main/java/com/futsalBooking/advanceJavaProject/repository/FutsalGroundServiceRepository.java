package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Futsal_Ground;
import com.futsalBooking.advanceJavaProject.service.FutsalBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FutsalGroundServiceRepository extends JpaRepository<Futsal_Ground,Integer> { List<Futsal_Ground> findByFutsal(Futsal futsal);
    @Query("SELECT f FROM Futsal_Ground f WHERE f.futsal.id = :futsalId")
    List<Futsal_Ground> findByFutsal_id(@Param("futsalId") int futsalId);

    @Query("SELECT f FROM Futsal_Ground f WHERE f.futsal.id = :futsalId")
    Futsal_Ground findByFutsalId(@Param("futsalId") int futsalId);


}
