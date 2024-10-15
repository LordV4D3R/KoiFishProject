package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Koi;
import com.sp23.koifishproject.service.KoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/kois")
public class KoiController {

    @Autowired
    private KoiService koiService;

    // Lấy tất cả Koi
    @GetMapping
    public ResponseEntity<List<Koi>> getAllKois() {
        List<Koi> kois = koiService.getAllKois();
        return ResponseEntity.ok(kois);
    }

    // Lấy Koi theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getKoiById(@PathVariable UUID id) {
        Optional<Koi> koi = koiService.getKoiById(id);
        return koi.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Koi not found"));
    }

    // Thêm mới Koi
    @PostMapping
    public ResponseEntity<Koi> addKoi(@RequestBody Koi koi) {
        Koi newKoi = koiService.addKoi(koi);
        return ResponseEntity.status(201).body(newKoi);
    }

    // Cập nhật Koi theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateKoiById(@PathVariable UUID id, @RequestBody Koi koi) {
        Optional<Koi> updatedKoi = koiService.updateKoiById(id, koi);
        return updatedKoi.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Koi not found"));
    }

    // Xóa Koi theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKoiById(@PathVariable UUID id) {
        Optional<Koi> koi = koiService.getKoiById(id);
        if (koi.isPresent()) {
            koiService.deleteKoiById(id);
            return ResponseEntity.status(204).body("Koi deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Koi not found");
        }
    }
}
