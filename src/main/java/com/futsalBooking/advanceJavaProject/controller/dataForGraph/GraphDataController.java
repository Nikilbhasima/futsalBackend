package com.futsalBooking.advanceJavaProject.controller.dataForGraph;

import com.futsalBooking.advanceJavaProject.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/dataForGraph")
public class GraphDataController {

    @Autowired
    private GraphService graphService;

    @GetMapping("/getBarGraphData")
    public ResponseEntity<Map<LocalDate,Integer>> getDataForBarGraph(Authentication auth) {

        Map<LocalDate,Integer> data = graphService.getBarGraphData(auth);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/getDataForPieChart")
    public ResponseEntity<Map<String,Integer>> getDataForPieChart(Authentication auth) {
        Map<String,Integer> data = graphService.getPieChartData(auth);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
