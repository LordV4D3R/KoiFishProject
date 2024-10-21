package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Pond;
import com.sp23.koifishproject.service.PondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ponds")
public class PondController {

    @Autowired
    private PondService pondService;

    // Lấy tất cả các pond
    @GetMapping
    public ResponseEntity<?> getAllPonds() {
        try {
            List<Pond> ponds = pondService.getAllPonds();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", ponds);  // Giữ nguyên danh sách ponds
            return ResponseEntity.ok(response);  // Trả về cả status và danh sách ponds
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve ponds: " + e.getMessage()));
        }
    }

    // Lấy pond theo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPondById(@PathVariable UUID id) {
        try {
            Optional<Pond> pond = pondService.getPondById(id);
            if (pond.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", pond.get());  // Trả về thông tin chi tiết của pond
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Pond not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve pond: " + e.getMessage()));
        }
    }

    // Thêm mới pond
    @PostMapping
    public ResponseEntity<?> addPond(@RequestBody Pond pond) {
        try {
            Pond newPond = pondService.addPond(pond);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Pond created successfully");
            response.put("data", newPond);  // Trả về thông tin pond vừa được tạo
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding pond: " + e.getMessage()));
        }
    }

    // Cập nhật pond theo id
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePondById(@PathVariable UUID id, @RequestBody Pond pond) {
        try {
            Optional<Pond> updatedPond = pondService.updatePondById(id, pond);
            if (updatedPond.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Pond updated successfully");
                response.put("data", updatedPond.get());  // Trả về thông tin pond đã cập nhật
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Pond not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update pond: " + e.getMessage()));
        }
    }

    // Xóa pond theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePondById(@PathVariable UUID id) {
        try {
            Optional<Pond> pond = pondService.getPondById(id);
            if (pond.isPresent()) {
                pondService.deletePondById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "Pond deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Pond not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete pond: " + e.getMessage()));
        }
    }
}
