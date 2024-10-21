package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.MeasureData;
import com.sp23.koifishproject.service.MeasureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/measure-data")
public class MeasureDataController {

    @Autowired
    private MeasureDataService measureDataService;

    // Lấy tất cả MeasureData
    @GetMapping
    public ResponseEntity<?> getAllMeasureData() {
        try {
            List<MeasureData> measureDataList = measureDataService.getAllMeasureData();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", measureDataList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve measure data: " + e.getMessage()));
        }
    }

    // Lấy MeasureData theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasureDataById(@PathVariable UUID id) {
        try {
            Optional<MeasureData> measureData = measureDataService.getMeasureDataById(id);
            if (measureData.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", measureData.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "MeasureData not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve measure data: " + e.getMessage()));
        }
    }

    // Thêm mới MeasureData
    @PostMapping
    public ResponseEntity<?> addMeasureData(@RequestBody MeasureData measureData) {
        try {
            MeasureData newMeasureData = measureDataService.addMeasureData(measureData);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "MeasureData created successfully");
            response.put("data", newMeasureData);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding measure data: " + e.getMessage()));
        }
    }

    // Cập nhật MeasureData theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeasureDataById(@PathVariable UUID id, @RequestBody MeasureData measureData) {
        try {
            Optional<MeasureData> updatedMeasureData = measureDataService.updateMeasureDataById(id, measureData);
            if (updatedMeasureData.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "MeasureData updated successfully");
                response.put("data", updatedMeasureData.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "MeasureData not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update measure data: " + e.getMessage()));
        }
    }

    // Xóa MeasureData theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeasureDataById(@PathVariable UUID id) {
        try {
            Optional<MeasureData> measureData = measureDataService.getMeasureDataById(id);
            if (measureData.isPresent()) {
                measureDataService.deleteMeasureDataById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "MeasureData deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "MeasureData not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete measure data: " + e.getMessage()));
        }
    }
}
