package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.MeasureData;
import com.sp23.koifishproject.service.MeasureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/measure-data")
public class MeasureDataController {

    @Autowired
    private MeasureDataService measureDataService;

    // Lấy tất cả MeasureData
    @GetMapping
    public ResponseEntity<List<MeasureData>> getAllMeasureData() {
        List<MeasureData> measureDataList = measureDataService.getAllMeasureData();
        return ResponseEntity.ok(measureDataList);
    }

    // Lấy MeasureData theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMeasureDataById(@PathVariable UUID id) {
        Optional<MeasureData> measureData = measureDataService.getMeasureDataById(id);
        return measureData.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("MeasureData not found"));
    }

    // Thêm mới MeasureData
    @PostMapping
    public ResponseEntity<MeasureData> addMeasureData(@RequestBody MeasureData measureData) {
        MeasureData newMeasureData = measureDataService.addMeasureData(measureData);
        return ResponseEntity.status(201).body(newMeasureData);
    }

    // Cập nhật MeasureData theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMeasureDataById(@PathVariable UUID id, @RequestBody MeasureData measureData) {
        Optional<MeasureData> updatedMeasureData = measureDataService.updateMeasureDataById(id, measureData);
        return updatedMeasureData.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("MeasureData not found"));
    }

    // Xóa MeasureData theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeasureDataById(@PathVariable UUID id) {
        Optional<MeasureData> measureData = measureDataService.getMeasureDataById(id);
        if (measureData.isPresent()) {
            measureDataService.deleteMeasureDataById(id);
            return ResponseEntity.status(204).body("MeasureData deleted successfully");
        } else {
            return ResponseEntity.status(404).body("MeasureData not found");
        }
    }
}
