package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.FeedingSchedule;
import com.sp23.koifishproject.service.FeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeding-schedules")
public class FeedingScheduleController {

    @Autowired
    private FeedingScheduleService feedingScheduleService;

    // Lấy tất cả FeedingSchedule
    @GetMapping
    public ResponseEntity<List<FeedingSchedule>> getAllFeedingSchedules() {
        List<FeedingSchedule> feedingSchedules = feedingScheduleService.getAllFeedingSchedules();
        return ResponseEntity.ok(feedingSchedules);
    }

    // Lấy FeedingSchedule theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFeedingScheduleById(@PathVariable UUID id) {
        Optional<FeedingSchedule> feedingSchedule = feedingScheduleService.getFeedingScheduleById(id);
        return feedingSchedule.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("FeedingSchedule not found"));
    }

    // Thêm mới FeedingSchedule
    @PostMapping
    public ResponseEntity<FeedingSchedule> addFeedingSchedule(@RequestBody FeedingSchedule feedingSchedule) {
        FeedingSchedule newFeedingSchedule = feedingScheduleService.addFeedingSchedule(feedingSchedule);
        return ResponseEntity.status(201).body(newFeedingSchedule);
    }

    // Cập nhật FeedingSchedule theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFeedingScheduleById(@PathVariable UUID id, @RequestBody FeedingSchedule feedingSchedule) {
        Optional<FeedingSchedule> updatedFeedingSchedule = feedingScheduleService.updateFeedingScheduleById(id, feedingSchedule);
        return updatedFeedingSchedule.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("FeedingSchedule not found"));
    }

    // Xóa FeedingSchedule theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedingScheduleById(@PathVariable UUID id) {
        Optional<FeedingSchedule> feedingSchedule = feedingScheduleService.getFeedingScheduleById(id);
        if (feedingSchedule.isPresent()) {
            feedingScheduleService.deleteFeedingScheduleById(id);
            return ResponseEntity.status(204).body("FeedingSchedule deleted successfully");
        } else {
            return ResponseEntity.status(404).body("FeedingSchedule not found");
        }
    }
}
