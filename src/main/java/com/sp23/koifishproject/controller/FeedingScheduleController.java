package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.FeedingSchedule;
import com.sp23.koifishproject.service.FeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/feeding-schedules")
public class FeedingScheduleController {

    @Autowired
    private FeedingScheduleService feedingScheduleService;

    // Lấy tất cả FeedingSchedule
    @GetMapping
    public ResponseEntity<?> getAllFeedingSchedules() {
        try {
            List<FeedingSchedule> feedingSchedules = feedingScheduleService.getAllFeedingSchedules();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", feedingSchedules);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve feeding schedules: " + e.getMessage()));
        }
    }

    // Lấy FeedingSchedule theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedingScheduleById(@PathVariable UUID id) {
        try {
            Optional<FeedingSchedule> feedingSchedule = feedingScheduleService.getFeedingScheduleById(id);
            if (feedingSchedule.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", feedingSchedule.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "FeedingSchedule not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve feeding schedule: " + e.getMessage()));
        }
    }

    // Thêm mới FeedingSchedule
    @PostMapping
    public ResponseEntity<?> addFeedingSchedule(@RequestBody FeedingSchedule feedingSchedule) {
        try {
            FeedingSchedule newFeedingSchedule = feedingScheduleService.addFeedingSchedule(feedingSchedule);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "FeedingSchedule created successfully");
            response.put("data", newFeedingSchedule);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error adding feeding schedule: " + e.getMessage()));
        }
    }

    // Cập nhật FeedingSchedule theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedingScheduleById(@PathVariable UUID id, @RequestBody FeedingSchedule feedingSchedule) {
        try {
            Optional<FeedingSchedule> updatedFeedingSchedule = feedingScheduleService.updateFeedingScheduleById(id, feedingSchedule);
            if (updatedFeedingSchedule.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "FeedingSchedule updated successfully");
                response.put("data", updatedFeedingSchedule.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "FeedingSchedule not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update feeding schedule: " + e.getMessage()));
        }
    }

    // Xóa FeedingSchedule theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedingScheduleById(@PathVariable UUID id) {
        try {
            Optional<FeedingSchedule> feedingSchedule = feedingScheduleService.getFeedingScheduleById(id);
            if (feedingSchedule.isPresent()) {
                feedingScheduleService.deleteFeedingScheduleById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "FeedingSchedule deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "FeedingSchedule not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete feeding schedule: " + e.getMessage()));
        }
    }
}
