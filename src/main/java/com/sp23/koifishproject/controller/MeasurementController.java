package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Measurement;
import com.sp23.koifishproject.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    // Lấy tất cả các đo lường
    @GetMapping
    public ResponseEntity<List<Measurement>> getAllMeasurements() {
        List<Measurement> measurements = measurementService.getAllMeasurements();
        return ResponseEntity.ok(measurements);
    }

    // Lấy đo lường theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMeasurementById(@PathVariable UUID id) {
        Optional<Measurement> measurement = measurementService.getMeasurementById(id);
        return measurement.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Measurement not found"));
    }

    // Thêm mới đo lường
    @PostMapping
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement measurement) {
        Measurement newMeasurement = measurementService.addMeasurement(measurement);
        return ResponseEntity.status(201).body(newMeasurement);
    }

    // Cập nhật đo lường theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMeasurementById(@PathVariable UUID id, @RequestBody Measurement measurement) {
        Optional<Measurement> updatedMeasurement = measurementService.updateMeasurementById(id, measurement);
        return updatedMeasurement.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Measurement not found"));
    }

    // Xóa đo lường theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeasurementById(@PathVariable UUID id) {
        Optional<Measurement> measurement = measurementService.getMeasurementById(id);
        if (measurement.isPresent()) {
            measurementService.deleteMeasurementById(id);
            return ResponseEntity.status(204).body("Measurement deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Measurement not found");
        }
    }
}
