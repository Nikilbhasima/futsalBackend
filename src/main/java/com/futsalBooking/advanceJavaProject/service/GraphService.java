package com.futsalBooking.advanceJavaProject.service;

import com.futsalBooking.advanceJavaProject.dto.FutsalDto;
import com.futsalBooking.advanceJavaProject.model.Futsal;
import com.futsalBooking.advanceJavaProject.model.Users;
import com.futsalBooking.advanceJavaProject.repository.FutsalBookingServiceeRepository;
import com.futsalBooking.advanceJavaProject.repository.UsersServiceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphService {

    @Autowired
    private FutsalBookingServiceeRepository futsalBookingServiceeRepository;

    @Autowired
    private UsersServiceRepository usersServiceRepository;
    public Map<LocalDate, Integer> getBarGraphData(Authentication authentication){
        Users user=usersServiceRepository.findByPhoneNumber(authentication.getName()).orElseThrow(()->new RuntimeException("User not found"));
        LocalDate today = LocalDate.now();
        List<LocalDate> weekDates = getWeekDates(today);
        Map<LocalDate,Integer> barGraphData = new HashMap<>();
        weekDates.forEach(date->{
            int numberOfBookings=futsalBookingServiceeRepository.countNumberOfBookingPerData(date,user.getFutsal().getId());
            barGraphData.put(date,numberOfBookings);
        });

        return barGraphData;
    }


    public Map<String,Integer> getPieChartData(Authentication auth) {
        Users user = usersServiceRepository.findByPhoneNumber(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();
        Map<String, LocalDate> weekBounds = getWeekBoundaries(today);
        String[] status = {"completed", "pending", "cancelled"};
        Map<String,Integer> pieChartData = new HashMap<>();

        for(String statusName : status) {
            int count = futsalBookingServiceeRepository.countDataForPieChart(
                    weekBounds.get("sunday"),
                    weekBounds.get("saturday"),
                    statusName,
                    user.getFutsal().getId());
            pieChartData.put(statusName, count);
        }
        return pieChartData;
    }



    public static List<LocalDate> getWeekDates(LocalDate date) {
        List<LocalDate> dates = new ArrayList<>();

        // Start of the week (Sunday)
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        // End of the week (Saturday)
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        // Add all days from start to end
        LocalDate current = startOfWeek;
        while (!current.isAfter(endOfWeek)) {
            dates.add(current);
            current = current.plusDays(1);
        }

        return dates;
    }
    public Map<String, LocalDate> getWeekBoundaries(LocalDate date) {
        Map<String, LocalDate> weekBoundaries = new HashMap<>();

        // Sunday of the current week
        LocalDate sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        // Saturday of the current week
        LocalDate saturday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        weekBoundaries.put("sunday", sunday);
        weekBoundaries.put("saturday", saturday);

        return weekBoundaries;
    }

}
