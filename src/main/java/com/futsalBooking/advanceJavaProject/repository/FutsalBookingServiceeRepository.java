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
    @Query("SELECT b FROM Futsal_Booking b WHERE b.futsal_ground.id = :groundId AND b.playing_date = :playingDate  AND b.status != 'cancelled'")
    List<Futsal_Booking> findBookingsByGroundId(@Param("groundId") int groundId, @Param("playingDate") LocalDate playingDate);

    @Query("SELECT b FROM Futsal_Booking b WHERE b.challenger_id.id = :userId AND b.bookingType= :bookingType")
    List<Futsal_Booking> findByChallenger_id(@Param("userId") int userId,@Param("bookingType") String bookingType);

    @Query("SELECT b FROM Futsal_Booking b WHERE b.bookingType = 'challenge'")
    List<Futsal_Booking> findBookingByChallenge();

    @Query("SELECT b FROM Futsal_Booking b WHERE b.challenger_id.id = :userId AND b.bookingType = 'challenge' ")
    List<Futsal_Booking> findBookingByChallengerId(@Param("userId") int userId);

    @Query("SELECT b FROM Futsal_Booking b WHERE b.opponent_id.id = :userId AND b.bookingType = 'challenge' ")
    List<Futsal_Booking> findBookingByOpponent(@Param("userId") int userId );


    @Query("SELECT COUNT(b) FROM Futsal_Booking b " +
            "WHERE b.playing_date = :date " +
            "AND b.futsal_ground.futsal.id = :futsalId")
    int countNumberOfBookingPerData(@Param("date") LocalDate date,
                                    @Param("futsalId") int futsalId);

    @Query("SELECT COUNT(b) FROM Futsal_Booking b " +
            "WHERE b.status = :status " +
            "AND b.futsal_ground.futsal.id = :futsalId " +
            "AND b.playing_date BETWEEN :startDate AND :endDate")
    int countDataForPieChart(@Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate,
                             @Param("status") String status,
                             @Param("futsalId") int futsalId);
}
