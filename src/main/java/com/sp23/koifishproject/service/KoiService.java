package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Koi;
import com.sp23.koifishproject.repository.mongo.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KoiService {

    @Autowired
    private KoiRepository koiRepository;

    // Lấy tất cả Koi
    public List<Koi> getAllKois() {
        return koiRepository.findAll();
    }

    // Lấy Koi theo ID
    public Optional<Koi> getKoiById(UUID id) {
        return koiRepository.findById(id);
    }

    // Thêm mới Koi
    public Koi addKoi(Koi koi) {
        // Generate UUID nếu chưa có
        if (koi.getId() == null) {
            koi.setId(UUID.randomUUID());
        }
        return koiRepository.save(koi);
    }

    // Cập nhật Koi theo ID
    public Optional<Koi> updateKoiById(UUID id, Koi koiDetails) {
        return koiRepository.findById(id).map(existingKoi -> {
            existingKoi.setName(koiDetails.getName());
            existingKoi.setAge(koiDetails.getAge());
            existingKoi.setSex(koiDetails.getSex());
            existingKoi.setCategory(koiDetails.getCategory());
            existingKoi.setInPondSince(koiDetails.getInPondSince());
            existingKoi.setPurchasePrice(koiDetails.getPurchasePrice());
            existingKoi.setStatus(koiDetails.getStatus());
            existingKoi.setImgUrl(koiDetails.getImgUrl());
            existingKoi.setOrigin(koiDetails.getOrigin());
            existingKoi.setBreed(koiDetails.getBreed());
            existingKoi.setKoiRecords(koiDetails.getKoiRecords());
            existingKoi.setFeedingSchedules(koiDetails.getFeedingSchedules());
            return koiRepository.save(existingKoi);
        });
    }

    // Xóa Koi theo ID
    public void deleteKoiById(UUID id) {
        koiRepository.deleteById(id);
    }
}