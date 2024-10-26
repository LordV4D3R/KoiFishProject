package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.CheckFeed;
import com.sp23.koifishproject.model.FeedingSchedule;
import com.sp23.koifishproject.repository.mongo.CheckFeedRepository;
import com.sp23.koifishproject.repository.mongo.FeedingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckFeedService {

    @Autowired
    private CheckFeedRepository checkFeedRepository;

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    // Lấy tất cả CheckFeed
    public List<CheckFeed> getAllCheckFeeds() {
        return checkFeedRepository.findAll();
    }

    // Lấy CheckFeed theo ID
    public Optional<CheckFeed> getCheckFeedById(UUID id) {
        return checkFeedRepository.findById(id);
    }

    // Thêm mới CheckFeed
    public CheckFeed addCheckFeed(CheckFeed checkFeed) {
        if (checkFeed.getId() == null) {
            checkFeed.setId(UUID.randomUUID());
        }

        // Lưu CheckFeed trước
        CheckFeed savedCheckFeed = checkFeedRepository.save(checkFeed);

        // Thêm ID của CheckFeed vào danh sách checkFeedID của FeedingSchedule tương ứng
        Optional<FeedingSchedule> feedingScheduleOptional = feedingScheduleRepository.findById(checkFeed.getFeedingScheduleId());
        if (feedingScheduleOptional.isPresent()) {
            FeedingSchedule feedingSchedule = feedingScheduleOptional.get();
            feedingSchedule.getCheckFeedID().add(savedCheckFeed.getId());
            feedingScheduleRepository.save(feedingSchedule); // Lưu lại FeedingSchedule sau khi cập nhật
        }

        return savedCheckFeed;
    }

    // Cập nhật CheckFeed theo ID
    public Optional<CheckFeed> updateCheckFeedById(UUID id, CheckFeed checkFeedDetails) {
        return checkFeedRepository.findById(id).map(existingCheckFeed -> {
            existingCheckFeed.setStatus(checkFeedDetails.isStatus());
            existingCheckFeed.setTimeCheck(checkFeedDetails.getTimeCheck());
            existingCheckFeed.setFeedingScheduleId(checkFeedDetails.getFeedingScheduleId());
            return checkFeedRepository.save(existingCheckFeed);
        });
    }

    // Xóa CheckFeed theo ID
    public void deleteCheckFeedById(UUID id) {
        checkFeedRepository.deleteById(id);
    }
}
