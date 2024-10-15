package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.DevelopmentStage;
import com.sp23.koifishproject.repository.mongo.DevelopmentStageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DevelopmentStageService {

    @Autowired
    private DevelopmentStageRepository developmentStageRepository;

    // Lấy tất cả DevelopmentStage
    public List<DevelopmentStage> getAllDevelopmentStages() {
        return developmentStageRepository.findAll();
    }

    // Lấy DevelopmentStage theo ID
    public Optional<DevelopmentStage> getDevelopmentStageById(UUID id) {
        return developmentStageRepository.findById(id);
    }

    // Thêm mới DevelopmentStage
    public DevelopmentStage addDevelopmentStage(DevelopmentStage developmentStage) {
        // Generate UUID nếu chưa có
        if (developmentStage.getId() == null) {
            developmentStage.setId(UUID.randomUUID());
        }
        return developmentStageRepository.save(developmentStage);
    }

    // Cập nhật DevelopmentStage theo ID
    public Optional<DevelopmentStage> updateDevelopmentStageById(UUID id, DevelopmentStage developmentStageDetails) {
        return developmentStageRepository.findById(id).map(existingDevelopmentStage -> {
            existingDevelopmentStage.setStageName(developmentStageDetails.getStageName());
            existingDevelopmentStage.setRequiredFoodAmount(developmentStageDetails.getRequiredFoodAmount());
            existingDevelopmentStage.setKoiRecords(developmentStageDetails.getKoiRecords());
            return developmentStageRepository.save(existingDevelopmentStage);
        });
    }

    // Xóa DevelopmentStage theo ID
    public void deleteDevelopmentStageById(UUID id) {
        developmentStageRepository.deleteById(id);
    }
}
