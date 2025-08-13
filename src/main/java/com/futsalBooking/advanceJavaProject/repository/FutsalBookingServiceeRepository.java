package com.futsalBooking.advanceJavaProject.repository;

import com.futsalBooking.advanceJavaProject.model.Futsal_Booking;
import com.futsalBooking.advanceJavaProject.service.FutsalBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FutsalBookingServiceeRepository extends JpaRepository<Futsal_Booking,Integer> {
    @Query("SELECT b FROM Futsal_Booking b WHERE b.futsal_ground.id = :groundId AND b.playing_date = :playingDate")
    List<Futsal_Booking> findBookingsByGroundId(@Param("groundId") int groundId, @Param("playingDate") LocalDate playingDate);

    @Query("SELECT b FROM Futsal_Booking b WHERE b.challenger_id.id = :userId AND b.bookingType= :bookingType")
    List<Futsal_Booking> findByChallenger_id(@Param("userId") int userId,@Param("bookingType") String bookingType);

    @Query("SELECT b FROM Futsal_Booking b WHERE b.bookingType = 'challenge'")
    List<Futsal_Booking> findBookingByChallenge();
}
