package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Unit;
import com.sp23.koifishproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // Lấy tất cả các đơn vị
    @GetMapping
    public ResponseEntity<?> getAllUnits() {
        try {
            List<Unit> units = unitService.getAllUnits();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", units);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve units: " + e.getMessage()));
        }
    }

    // Lấy đơn vị theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable UUID id) {
        try {
            Optional<Unit> unit = unitService.getUnitById(id);
            if (unit.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", unit.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Unit not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve unit: " + e.getMessage()));
        }
    }

    // Thêm mới đơn vị
    @PostMapping
    public ResponseEntity<?> addUnit(@RequestBody Unit unit) {
        try {
            Unit newUnit = unitService.addUnit(unit);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Unit created successfully");
            response.put("data", newUnit);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding unit: " + e.getMessage()));
        }
    }

    // Cập nhật đơn vị theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUnitById(@PathVariable UUID id, @RequestBody Unit unit) {
        try {
            Optional<Unit> updatedUnit = unitService.updateUnitById(id, unit);
            if (updatedUnit.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Unit updated successfully");
                response.put("data", updatedUnit.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Unit not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update unit: " + e.getMessage()));
        }
    }

    // Xóa đơn vị theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUnitById(@PathVariable UUID id) {
        try {
            Optional<Unit> unit = unitService.getUnitById(id);
            if (unit.isPresent()) {
                unitService.deleteUnitById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "Unit deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Unit not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete unit: " + e.getMessage()));
        }
    }
}
