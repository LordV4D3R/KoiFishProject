package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.KoiRecord;
import com.sp23.koifishproject.repository.mongo.KoiRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KoiRecordService {

    @Autowired
    private KoiRecordRepository koiRecordRepository;

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
        return koiRecordRepository.save(koiRecord);
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
