package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.FeedingSchedule;
import com.sp23.koifishproject.model.Koi;
import com.sp23.koifishproject.repository.mongo.FeedingScheduleRepository;
import com.sp23.koifishproject.repository.mongo.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedingScheduleService {

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    private KoiRepository koiRepository;

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

        // Lưu FeedingSchedule
        FeedingSchedule savedFeedingSchedule = feedingScheduleRepository.save(feedingSchedule);

        // Tìm Koi bằng koiId từ FeedingSchedule
        Optional<Koi> koiOptional = koiRepository.findById(feedingSchedule.getKoiId());
        if (koiOptional.isPresent()) {
            Koi koi = koiOptional.get();

            // Thêm ID của FeedingSchedule vào danh sách feedingSchedules của Koi
            koi.getFeedingSchedules().add(savedFeedingSchedule.getId());

            // Lưu lại Koi sau khi cập nhật
            koiRepository.save(koi);
        }

        return savedFeedingSchedule;
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
