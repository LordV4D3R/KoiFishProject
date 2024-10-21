package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Measurement;
import com.sp23.koifishproject.repository.mongo.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sp23.koifishproject.model.Pond;
import com.sp23.koifishproject.repository.mongo.PondRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private PondRepository pondRepository;

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

        // Lưu measurement
        Measurement savedMeasurement = measurementRepository.save(measurement);

        // Tìm pond theo ID từ measurement
        Optional<Pond> pondOptional = pondRepository.findById(measurement.getPondId());
        if (pondOptional.isPresent()) {
            Pond pond = pondOptional.get();

            // Thêm ID của measurement vào danh sách measurements của pond
            pond.getMeasurements().add(savedMeasurement.getId());

            // Lưu pond sau khi cập nhật
            pondRepository.save(pond);
        }

        return savedMeasurement;
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
