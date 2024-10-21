package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Measurement;
import com.sp23.koifishproject.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    // Lấy tất cả các đo lường
    @GetMapping
    public ResponseEntity<?> getAllMeasurements() {
        try {
            List<Measurement> measurements = measurementService.getAllMeasurements();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", measurements);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve measurements: " + e.getMessage()));
        }
    }

    // Lấy đo lường theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurementById(@PathVariable UUID id) {
        try {
            Optional<Measurement> measurement = measurementService.getMeasurementById(id);
            if (measurement.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", measurement.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Measurement not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve measurement: " + e.getMessage()));
        }
    }

    // Thêm mới đo lường
    @PostMapping
    public ResponseEntity<?> addMeasurement(@RequestBody Measurement measurement) {
        try {
            Measurement newMeasurement = measurementService.addMeasurement(measurement);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Measurement created successfully");
            response.put("data", newMeasurement);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding measurement: " + e.getMessage()));
        }
    }

    // Cập nhật đo lường theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeasurementById(@PathVariable UUID id, @RequestBody Measurement measurement) {
        try {
            Optional<Measurement> updatedMeasurement = measurementService.updateMeasurementById(id, measurement);
            if (updatedMeasurement.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Measurement updated successfully");
                response.put("data", updatedMeasurement.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Measurement not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update measurement: " + e.getMessage()));
        }
    }

    // Xóa đo lường theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeasurementById(@PathVariable UUID id) {
        try {
            Optional<Measurement> measurement = measurementService.getMeasurementById(id);
            if (measurement.isPresent()) {
                measurementService.deleteMeasurementById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "Measurement deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Measurement not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete measurement: " + e.getMessage()));
        }
    }
}
