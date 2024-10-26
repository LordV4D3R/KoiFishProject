package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.CheckFeed;
import com.sp23.koifishproject.service.CheckFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/check-feeds")
public class CheckFeedController {

    @Autowired
    private CheckFeedService checkFeedService;

    // Lấy tất cả CheckFeeds
    @GetMapping
    public ResponseEntity<?> getAllCheckFeeds() {
        try {
            List<CheckFeed> checkFeeds = checkFeedService.getAllCheckFeeds();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", checkFeeds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve check feeds: " + e.getMessage()));
        }
    }

    // Lấy CheckFeed theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckFeedById(@PathVariable UUID id) {
        try {
            Optional<CheckFeed> checkFeed = checkFeedService.getCheckFeedById(id);
            if (checkFeed.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", checkFeed.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "CheckFeed not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve check feed: " + e.getMessage()));
        }
    }

    // Thêm mới CheckFeed
    @PostMapping
    public ResponseEntity<?> addCheckFeed(@RequestBody CheckFeed checkFeed) {
        try {
            CheckFeed newCheckFeed = checkFeedService.addCheckFeed(checkFeed);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "CheckFeed created successfully");
            response.put("data", newCheckFeed);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding check feed: " + e.getMessage()));
        }
    }

    // Cập nhật CheckFeed theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCheckFeedById(@PathVariable UUID id, @RequestBody CheckFeed checkFeed) {
        try {
            Optional<CheckFeed> updatedCheckFeed = checkFeedService.updateCheckFeedById(id, checkFeed);
            if (updatedCheckFeed.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "CheckFeed updated successfully");
                response.put("data", updatedCheckFeed.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "CheckFeed not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update check feed: " + e.getMessage()));
        }
    }

    // Xóa CheckFeed theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCheckFeedById(@PathVariable UUID id) {
        try {
            Optional<CheckFeed> checkFeed = checkFeedService.getCheckFeedById(id);
            if (checkFeed.isPresent()) {
                checkFeedService.deleteCheckFeedById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "CheckFeed deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "CheckFeed not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete check feed: " + e.getMessage()));
        }
    }
}
