package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.MeasureData;
import com.sp23.koifishproject.model.Unit;
import com.sp23.koifishproject.repository.mongo.MeasureDataRepository;
import com.sp23.koifishproject.repository.mongo.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasureDataService {

    @Autowired
    private MeasureDataRepository measureDataRepository;

    @Autowired
    private UnitRepository unitRepository;

    // Lấy tất cả MeasureData
    public List<MeasureData> getAllMeasureData() {
        return measureDataRepository.findAll();
    }

    // Lấy MeasureData theo ID
    public Optional<MeasureData> getMeasureDataById(UUID id) {
        return measureDataRepository.findById(id);
    }

    // Thêm mới MeasureData
    public MeasureData addMeasureData(MeasureData measureData) {
        // Generate UUID nếu chưa có
        if (measureData.getId() == null) {
            measureData.setId(UUID.randomUUID());
        }

        MeasureData savedMeasureData = measureDataRepository.save(measureData);

        // Cập nhật Unit để gán measureData
        for (UUID unitId : measureData.getUnitIds()) {
            unitRepository.findById(unitId).ifPresent(unit -> {
                unit.setMeasureData(savedMeasureData.getId());
                unitRepository.save(unit);
            });
        }

        return savedMeasureData;
    }

    // Cập nhật MeasureData theo ID
    public Optional<MeasureData> updateMeasureDataById(UUID id, MeasureData measureDataDetails) {
        return measureDataRepository.findById(id).map(existingMeasureData -> {
            existingMeasureData.setMeasurementId(measureDataDetails.getMeasurementId());
            existingMeasureData.setUnitIds(measureDataDetails.getUnitIds());
            existingMeasureData.setVolume(measureDataDetails.getVolume());
            return measureDataRepository.save(existingMeasureData);
        });
    }

    // Xóa MeasureData theo ID
    public void deleteMeasureDataById(UUID id) {
        measureDataRepository.deleteById(id);
    }
}
