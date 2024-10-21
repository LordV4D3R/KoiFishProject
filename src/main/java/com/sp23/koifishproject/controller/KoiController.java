package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Koi;
import com.sp23.koifishproject.service.KoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/kois")
public class KoiController {

    @Autowired
    private KoiService koiService;

    // Lấy tất cả Koi
    @GetMapping
    public ResponseEntity<?> getAllKois() {
        try {
            List<Koi> kois = koiService.getAllKois();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", kois);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve kois: " + e.getMessage()));
        }
    }

    // Lấy Koi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getKoiById(@PathVariable UUID id) {
        try {
            Optional<Koi> koi = koiService.getKoiById(id);
            if (koi.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", koi.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Koi not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve koi: " + e.getMessage()));
        }
    }

    // Thêm mới Koi
    @PostMapping
    public ResponseEntity<?> addKoi(@RequestBody Koi koi) {
        try {
            Koi newKoi = koiService.addKoi(koi);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Koi created successfully");
            response.put("data", newKoi);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding koi: " + e.getMessage()));
        }
    }

    // Cập nhật Koi theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateKoiById(@PathVariable UUID id, @RequestBody Koi koi) {
        try {
            Optional<Koi> updatedKoi = koiService.updateKoiById(id, koi);
            if (updatedKoi.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Koi updated successfully");
                response.put("data", updatedKoi.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Koi not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update koi: " + e.getMessage()));
        }
    }

    // Xóa Koi theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKoiById(@PathVariable UUID id) {
        try {
            Optional<Koi> koi = koiService.getKoiById(id);
            if (koi.isPresent()) {
                koiService.deleteKoiById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "Koi deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Koi not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete koi: " + e.getMessage()));
        }
    }
}
