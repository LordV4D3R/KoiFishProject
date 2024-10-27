package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Unit;
import com.sp23.koifishproject.model.MeasureData;
import com.sp23.koifishproject.repository.mongo.UnitRepository;
import com.sp23.koifishproject.repository.mongo.MeasureDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private MeasureDataRepository measureDataRepository;

    // Lấy tất cả các đơn vị (Unit)
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    // Lấy Unit theo ID
    public Optional<Unit> getUnitById(UUID id) {
        return unitRepository.findById(id);
    }

    // Thêm mới Unit
    public Unit addUnit(Unit unit) {
        // Generate UUID nếu chưa có
        if (unit.getId() == null) {
            unit.setId(UUID.randomUUID());
        }

        Unit savedUnit = unitRepository.save(unit);

        // Tự động thêm Unit vào MeasureData tương ứng
        if (unit.getMeasureData() != null) {
            measureDataRepository.findById(unit.getMeasureData()).ifPresent(measureData -> {
                measureData.getUnitIds().add(savedUnit.getId());
                measureDataRepository.save(measureData);
            });
        }

        return savedUnit;
    }

    // Cập nhật Unit theo ID
    public Optional<Unit> updateUnitById(UUID id, Unit unitDetails) {
        return unitRepository.findById(id).map(existingUnit -> {
            existingUnit.setUnitName(unitDetails.getUnitName());
            existingUnit.setUnitFullName(unitDetails.getUnitFullName());
            existingUnit.setUnitValue(unitDetails.getUnitValue());
            existingUnit.setInfo(unitDetails.getInfo());
            existingUnit.setMinValue(unitDetails.getMinValue());
            existingUnit.setMaxValue(unitDetails.getMaxValue());
            existingUnit.setMeasureData(unitDetails.getMeasureData());
            return unitRepository.save(existingUnit);
        });
    }

    // Xóa Unit theo ID
    public void deleteUnitById(UUID id) {
        unitRepository.deleteById(id);
    }
}
