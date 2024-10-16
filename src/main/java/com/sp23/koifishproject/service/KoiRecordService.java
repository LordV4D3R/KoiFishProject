package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.KoiRecord;
import com.sp23.koifishproject.model.Koi;
import com.sp23.koifishproject.model.DevelopmentStage;
import com.sp23.koifishproject.repository.mongo.DevelopmentStageRepository;
import com.sp23.koifishproject.repository.mongo.KoiRecordRepository;
import com.sp23.koifishproject.repository.mongo.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KoiRecordService {

    @Autowired
    private KoiRecordRepository koiRecordRepository;
    @Autowired
    private KoiRepository koiRepository;
    @Autowired
    private DevelopmentStageRepository developmentStageRepository;

    // Lấy tất cả KoiRecord
    public List<KoiRecord> getAllKoiRecords() {
        return koiRecordRepository.findAll();
    }

    // Lấy KoiRecord theo ID
    public Optional<KoiRecord> getKoiRecordById(UUID id) {
        return koiRecordRepository.findById(id);
    }

    // Thêm mới KoiRecord
    public KoiRecord addKoiRecord(KoiRecord koiRecord) {
        // Generate UUID nếu chưa có
        if (koiRecord.getId() == null) {
            koiRecord.setId(UUID.randomUUID());
        }

        // Lưu KoiRecord
        KoiRecord savedKoiRecord = koiRecordRepository.save(koiRecord);

        // Tìm Koi bằng koiId từ KoiRecord
        Optional<Koi> koiOptional = koiRepository.findById(koiRecord.getKoiId());
        if (koiOptional.isPresent()) {
            Koi koi = koiOptional.get();
            // Thêm ID của KoiRecord vào danh sách koiRecords của Koi
            koi.getKoiRecords().add(savedKoiRecord.getId());
            // Lưu Koi sau khi cập nhật
            koiRepository.save(koi);
        }

        // Tìm DevelopmentStage bằng developmentStageId từ KoiRecord
        Optional<DevelopmentStage> developmentStageOptional = developmentStageRepository.findById(koiRecord.getDevelopmentStageId());
        if (developmentStageOptional.isPresent()) {
            DevelopmentStage developmentStage = developmentStageOptional.get();
            // Thêm ID của KoiRecord vào danh sách koiRecords của DevelopmentStage
            developmentStage.getKoiRecords().add(savedKoiRecord.getId());
            // Lưu DevelopmentStage sau khi cập nhật
            developmentStageRepository.save(developmentStage);
        }

        return savedKoiRecord;
    }

    // Cập nhật KoiRecord theo ID
    public Optional<KoiRecord> updateKoiRecordById(UUID id, KoiRecord koiRecordDetails) {
        return koiRecordRepository.findById(id).map(existingKoiRecord -> {
            existingKoiRecord.setKoiId(koiRecordDetails.getKoiId());
            existingKoiRecord.setDevelopmentStageId(koiRecordDetails.getDevelopmentStageId());
            existingKoiRecord.setWeight(koiRecordDetails.getWeight());
            existingKoiRecord.setRecordOn(koiRecordDetails.getRecordOn());
            existingKoiRecord.setLength(koiRecordDetails.getLength());
            existingKoiRecord.setPhysique(koiRecordDetails.getPhysique());
            return koiRecordRepository.save(existingKoiRecord);
        });
    }

    // Xóa KoiRecord theo ID
    public void deleteKoiRecordById(UUID id) {
        koiRecordRepository.deleteById(id);
    }
}
