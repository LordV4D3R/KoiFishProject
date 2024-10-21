package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.DevelopmentStage;
import com.sp23.koifishproject.service.DevelopmentStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/development-stages")
public class DevelopmentStageController {

    @Autowired
    private DevelopmentStageService developmentStageService;

    @GetMapping
    public ResponseEntity<?> getAllDevelopmentStages() {
        try {
            List<DevelopmentStage> developmentStages = developmentStageService.getAllDevelopmentStages();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", developmentStages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve development stages: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDevelopmentStageById(@PathVariable UUID id) {
        try {
            Optional<DevelopmentStage> developmentStage = developmentStageService.getDevelopmentStageById(id);
            if (developmentStage.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", developmentStage.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "DevelopmentStage not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve development stage: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addDevelopmentStage(@RequestBody DevelopmentStage developmentStage) {
        try {
            DevelopmentStage newDevelopmentStage = developmentStageService.addDevelopmentStage(developmentStage);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "DevelopmentStage created successfully");
            response.put("data", newDevelopmentStage);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding development stage: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDevelopmentStageById(@PathVariable UUID id, @RequestBody DevelopmentStage developmentStageDetails) {
        try {
            Optional<DevelopmentStage> updatedDevelopmentStage = developmentStageService.updateDevelopmentStageById(id, developmentStageDetails);
            if (updatedDevelopmentStage.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "DevelopmentStage updated successfully");
                response.put("data", updatedDevelopmentStage.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "DevelopmentStage not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update development stage: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevelopmentStageById(@PathVariable UUID id) {
        try {
            Optional<DevelopmentStage> developmentStage = developmentStageService.getDevelopmentStageById(id);
            if (developmentStage.isPresent()) {
                developmentStageService.deleteDevelopmentStageById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "DevelopmentStage deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "DevelopmentStage not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete development stage: " + e.getMessage()));
        }
    }
}

