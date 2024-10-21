package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.KoiRecord;
import com.sp23.koifishproject.service.KoiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/koi-records")
public class KoiRecordController {

    @Autowired
    private KoiRecordService koiRecordService;

    // Lấy tất cả KoiRecord
    @GetMapping
    public ResponseEntity<?> getAllKoiRecords() {
        try {
            List<KoiRecord> koiRecords = koiRecordService.getAllKoiRecords();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", koiRecords);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve koi records: " + e.getMessage()));
        }
    }

    // Lấy KoiRecord theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getKoiRecordById(@PathVariable UUID id) {
        try {
            Optional<KoiRecord> koiRecord = koiRecordService.getKoiRecordById(id);
            if (koiRecord.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", koiRecord.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "KoiRecord not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve koi record: " + e.getMessage()));
        }
    }

    // Thêm mới KoiRecord
    @PostMapping
    public ResponseEntity<?> addKoiRecord(@RequestBody KoiRecord koiRecord) {
        try {
            KoiRecord newKoiRecord = koiRecordService.addKoiRecord(koiRecord);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "KoiRecord created successfully");
            response.put("data", newKoiRecord);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding koi record: " + e.getMessage()));
        }
    }

    // Cập nhật KoiRecord theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateKoiRecordById(@PathVariable UUID id, @RequestBody KoiRecord koiRecord) {
        try {
            Optional<KoiRecord> updatedKoiRecord = koiRecordService.updateKoiRecordById(id, koiRecord);
            if (updatedKoiRecord.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "KoiRecord updated successfully");
                response.put("data", updatedKoiRecord.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "KoiRecord not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update koi record: " + e.getMessage()));
        }
    }

    // Xóa KoiRecord theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKoiRecordById(@PathVariable UUID id) {
        try {
            Optional<KoiRecord> koiRecord = koiRecordService.getKoiRecordById(id);
            if (koiRecord.isPresent()) {
                koiRecordService.deleteKoiRecordById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "KoiRecord deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "KoiRecord not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete koi record: " + e.getMessage()));
        }
    }
}
