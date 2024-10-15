package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.KoiRecord;
import com.sp23.koifishproject.service.KoiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/koi-records")
public class KoiRecordController {

    @Autowired
    private KoiRecordService koiRecordService;

    // Lấy tất cả KoiRecord
    @GetMapping
    public ResponseEntity<List<KoiRecord>> getAllKoiRecords() {
        List<KoiRecord> koiRecords = koiRecordService.getAllKoiRecords();
        return ResponseEntity.ok(koiRecords);
    }

    // Lấy KoiRecord theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getKoiRecordById(@PathVariable UUID id) {
        Optional<KoiRecord> koiRecord = koiRecordService.getKoiRecordById(id);
        return koiRecord.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("KoiRecord not found"));
    }

    // Thêm mới KoiRecord
    @PostMapping
    public ResponseEntity<KoiRecord> addKoiRecord(@RequestBody KoiRecord koiRecord) {
        KoiRecord newKoiRecord = koiRecordService.addKoiRecord(koiRecord);
        return ResponseEntity.status(201).body(newKoiRecord);
    }

    // Cập nhật KoiRecord theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateKoiRecordById(@PathVariable UUID id, @RequestBody KoiRecord koiRecord) {
        Optional<KoiRecord> updatedKoiRecord = koiRecordService.updateKoiRecordById(id, koiRecord);
        return updatedKoiRecord.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("KoiRecord not found"));
    }

    // Xóa KoiRecord theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKoiRecordById(@PathVariable UUID id) {
        Optional<KoiRecord> koiRecord = koiRecordService.getKoiRecordById(id);
        if (koiRecord.isPresent()) {
            koiRecordService.deleteKoiRecordById(id);
            return ResponseEntity.status(204).body("KoiRecord deleted successfully");
        } else {
            return ResponseEntity.status(404).body("KoiRecord not found");
        }
    }
}
