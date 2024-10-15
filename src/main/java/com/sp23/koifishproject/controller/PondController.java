package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.Pond;
import com.sp23.koifishproject.service.PondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/ponds")
public class PondController {

    @Autowired
    private PondService pondService;

    // Lấy tất cả các pond
    @GetMapping
    public ResponseEntity<List<Pond>> getAllPonds() {
        List<Pond> ponds = pondService.getAllPonds();
        return ResponseEntity.ok(ponds);
    }

    // Lấy pond theo id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPondById(@PathVariable UUID id) {
        Optional<Pond> pond = pondService.getPondById(id);
        return pond.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Pond not found"));
    }

    // Thêm mới pond
    @PostMapping
    public ResponseEntity<Pond> addPond(@RequestBody Pond pond) {
        Pond newPond = pondService.addPond(pond);
        return ResponseEntity.status(201).body(newPond);
    }

    // Cập nhật pond theo id
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePondById(@PathVariable UUID id, @RequestBody Pond pond) {
        Optional<Pond> updatedPond = pondService.updatePondById(id, pond);
        return updatedPond.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Pond not found"));
    }

    // Xóa pond theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePondById(@PathVariable UUID id) {
        Optional<Pond> pond = pondService.getPondById(id);
        if (pond.isPresent()) {
            pondService.deletePondById(id);
            return ResponseEntity.status(204).body("Pond deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Pond not found");
        }
    }
}
