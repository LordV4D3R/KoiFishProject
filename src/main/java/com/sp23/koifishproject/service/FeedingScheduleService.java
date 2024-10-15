package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.FeedingSchedule;
import com.sp23.koifishproject.repository.mongo.FeedingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedingScheduleService {

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    // Lấy tất cả FeedingSchedule
    public List<FeedingSchedule> getAllFeedingSchedules() {
        return feedingScheduleRepository.findAll();
    }

    // Lấy FeedingSchedule theo ID
    public Optional<FeedingSchedule> getFeedingScheduleById(UUID id) {
        return feedingScheduleRepository.findById(id);
    }

    // Thêm mới FeedingSchedule
    public FeedingSchedule addFeedingSchedule(FeedingSchedule feedingSchedule) {
        // Generate UUID nếu chưa có
        if (feedingSchedule.getId() == null) {
            feedingSchedule.setId(UUID.randomUUID());
        }
        return feedingScheduleRepository.save(feedingSchedule);
    }

    // Cập nhật FeedingSchedule theo ID
    public Optional<FeedingSchedule> updateFeedingScheduleById(UUID id, FeedingSchedule feedingScheduleDetails) {
        return feedingScheduleRepository.findById(id).map(existingFeedingSchedule -> {
            existingFeedingSchedule.setKoiId(feedingScheduleDetails.getKoiId());
            existingFeedingSchedule.setFedding(feedingScheduleDetails.getFedding());
            existingFeedingSchedule.setFoodAmount(feedingScheduleDetails.getFoodAmount());
            return feedingScheduleRepository.save(existingFeedingSchedule);
        });
    }

    // Xóa FeedingSchedule theo ID
    public void deleteFeedingScheduleById(UUID id) {
        feedingScheduleRepository.deleteById(id);
    }
}
