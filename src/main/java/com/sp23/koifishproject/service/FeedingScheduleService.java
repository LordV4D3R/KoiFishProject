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

    // Get all FeedingSchedules
    public List<FeedingSchedule> getAllFeedingSchedules() {
        return feedingScheduleRepository.findAll();
    }

    // Get FeedingSchedule by ID
    public Optional<FeedingSchedule> getFeedingScheduleById(UUID id) {
        return feedingScheduleRepository.findById(id);
    }

    // Add new FeedingSchedule
    public FeedingSchedule addFeedingSchedule(FeedingSchedule feedingSchedule) {
        if (feedingSchedule.getId() == null) {
            feedingSchedule.setId(UUID.randomUUID());
        }
        FeedingSchedule savedFeedingSchedule = feedingScheduleRepository.save(feedingSchedule);

        // Update Koi's feedingSchedules list
        Optional<Koi> koiOptional = koiRepository.findById(feedingSchedule.getKoiId());
        if (koiOptional.isPresent()) {
            Koi koi = koiOptional.get();
            koi.getFeedingSchedules().add(savedFeedingSchedule.getId());
            koiRepository.save(koi);
        }
        return savedFeedingSchedule;
    }

    // Update FeedingSchedule by ID
    public Optional<FeedingSchedule> updateFeedingScheduleById(UUID id, FeedingSchedule feedingScheduleDetails) {
        return feedingScheduleRepository.findById(id).map(existingFeedingSchedule -> {
            existingFeedingSchedule.setKoiId(feedingScheduleDetails.getKoiId());
            existingFeedingSchedule.setFeedAt(feedingScheduleDetails.getFeedAt());
            existingFeedingSchedule.setFoodAmount(feedingScheduleDetails.getFoodAmount());
            existingFeedingSchedule.setFoodType(feedingScheduleDetails.getFoodType());
            existingFeedingSchedule.setNote(feedingScheduleDetails.getNote());
            existingFeedingSchedule.setCheckFeedID(feedingScheduleDetails.getCheckFeedID());
            return feedingScheduleRepository.save(existingFeedingSchedule);
        });
    }

    // Delete FeedingSchedule by ID
    public void deleteFeedingScheduleById(UUID id) {
        feedingScheduleRepository.deleteById(id);
    }
}

