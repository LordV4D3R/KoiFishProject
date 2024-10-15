package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.DevelopmentStage;
import com.sp23.koifishproject.service.DevelopmentStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/development-stages")
public class DevelopmentStageController {

    @Autowired
    private DevelopmentStageService developmentStageService;

    // Lấy tất cả DevelopmentStage
    @GetMapping
    public ResponseEntity<List<DevelopmentStage>> getAllDevelopmentStages() {
        List<DevelopmentStage> developmentStages = developmentStageService.getAllDevelopmentStages();
        return ResponseEntity.ok(developmentStages);
    }

    // Lấy DevelopmentStage theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDevelopmentStageById(@PathVariable UUID id) {
        Optional<DevelopmentStage> developmentStage = developmentStageService.getDevelopmentStageById(id);
        return developmentStage.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("DevelopmentStage not found"));
    }

    // Thêm mới DevelopmentStage
    @PostMapping
    public ResponseEntity<DevelopmentStage> addDevelopmentStage(@RequestBody DevelopmentStage developmentStage) {
        DevelopmentStage newDevelopmentStage = developmentStageService.addDevelopmentStage(developmentStage);
        return ResponseEntity.status(201).body(newDevelopmentStage);
    }

    // Cập nhật DevelopmentStage theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDevelopmentStageById(@PathVariable UUID id, @RequestBody DevelopmentStage developmentStage) {
        Optional<DevelopmentStage> updatedDevelopmentStage = developmentStageService.updateDevelopmentStageById(id, developmentStage);
        return updatedDevelopmentStage.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("DevelopmentStage not found"));
    }

    // Xóa DevelopmentStage theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevelopmentStageById(@PathVariable UUID id) {
        Optional<DevelopmentStage> developmentStage = developmentStageService.getDevelopmentStageById(id);
        if (developmentStage.isPresent()) {
            developmentStageService.deleteDevelopmentStageById(id);
            return ResponseEntity.status(204).body("DevelopmentStage deleted successfully");
        } else {
            return ResponseEntity.status(404).body("DevelopmentStage not found");
        }
    }
}
