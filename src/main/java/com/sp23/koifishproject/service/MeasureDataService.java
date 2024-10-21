package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.MeasureData;
import com.sp23.koifishproject.model.Measurement;
import com.sp23.koifishproject.model.Unit;
import com.sp23.koifishproject.repository.mongo.MeasureDataRepository;
import com.sp23.koifishproject.repository.mongo.MeasurementRepository;
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
    private MeasurementRepository measurementRepository;
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

        // Lưu measureData
        MeasureData savedMeasureData = measureDataRepository.save(measureData);

        // Tìm Measurement theo ID từ MeasureData
        Optional<Measurement> measurementOptional = measurementRepository.findById(measureData.getMeasurementId());
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();

            // Thêm ID của MeasureData vào danh sách measureData của Measurement
            measurement.getMeasureData().add(savedMeasureData.getId());

            // Lưu Measurement sau khi cập nhật
            measurementRepository.save(measurement);
        }

        // Tìm Unit theo ID từ MeasureData
        Optional<Unit> unitOptional = unitRepository.findById(measureData.getUnitId());
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();

            // Thêm ID của MeasureData vào danh sách measureData của Unit
            unit.getMeasureData().add(savedMeasureData.getId());

            // Lưu Unit sau khi cập nhật
            unitRepository.save(unit);
        }

        return savedMeasureData;
    }

    // Cập nhật MeasureData theo ID
    public Optional<MeasureData> updateMeasureDataById(UUID id, MeasureData measureDataDetails) {
        return measureDataRepository.findById(id).map(existingMeasureData -> {
            existingMeasureData.setMeasurementId(measureDataDetails.getMeasurementId());
            existingMeasureData.setUnitId(measureDataDetails.getUnitId());
            existingMeasureData.setVolume(measureDataDetails.getVolume());
            return measureDataRepository.save(existingMeasureData);
        });
    }

    // Xóa MeasureData theo ID
    public void deleteMeasureDataById(UUID id) {
        measureDataRepository.deleteById(id);
    }
}
