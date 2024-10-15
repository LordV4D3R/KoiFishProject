package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Measurement;
import com.sp23.koifishproject.repository.mongo.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    // Lấy tất cả các đo lường
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    // Lấy thông tin đo lường theo ID
    public Optional<Measurement> getMeasurementById(UUID id) {
        return measurementRepository.findById(id);
    }

    // Thêm mới đo lường
    public Measurement addMeasurement(Measurement measurement) {
        // Generate UUID nếu chưa có
        if (measurement.getId() == null) {
            measurement.setId(UUID.randomUUID());
        }
        return measurementRepository.save(measurement);
    }

    // Cập nhật đo lường theo ID
    public Optional<Measurement> updateMeasurementById(UUID id, Measurement measurementDetails) {
        return measurementRepository.findById(id).map(existingMeasurement -> {
            existingMeasurement.setPondId(measurementDetails.getPondId());
            existingMeasurement.setMeasureData(measurementDetails.getMeasureData());
            existingMeasurement.setMeasureOn(measurementDetails.getMeasureOn());
            existingMeasurement.setNote(measurementDetails.getNote());
            return measurementRepository.save(existingMeasurement);
        });
    }

    // Xóa đo lường theo ID
    public void deleteMeasurementById(UUID id) {
        measurementRepository.deleteById(id);
    }
}
