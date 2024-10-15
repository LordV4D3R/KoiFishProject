package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Unit;
import com.sp23.koifishproject.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    // Lấy tất cả các đơn vị
    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.getAllUnits();
        return ResponseEntity.ok(units);
    }

    // Lấy đơn vị theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUnitById(@PathVariable UUID id) {
        Optional<Unit> unit = unitService.getUnitById(id);
        return unit.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Unit not found"));
    }

    // Thêm mới đơn vị
    @PostMapping
    public ResponseEntity<Unit> addUnit(@RequestBody Unit unit) {
        Unit newUnit = unitService.addUnit(unit);
        return ResponseEntity.status(201).body(newUnit);
    }

    // Cập nhật đơn vị theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUnitById(@PathVariable UUID id, @RequestBody Unit unit) {
        Optional<Unit> updatedUnit = unitService.updateUnitById(id, unit);
        return updatedUnit.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Unit not found"));
    }

    // Xóa đơn vị theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnitById(@PathVariable UUID id) {
        Optional<Unit> unit = unitService.getUnitById(id);
        if (unit.isPresent()) {
            unitService.deleteUnitById(id);
            return ResponseEntity.status(204).body("Unit deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Unit not found");
        }
    }
}
