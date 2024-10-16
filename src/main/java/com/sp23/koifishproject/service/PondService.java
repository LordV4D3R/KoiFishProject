package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Pond;
import com.sp23.koifishproject.repository.mongo.PondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.repository.mongo.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PondService {

    @Autowired
    private PondRepository pondRepository;
    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả các pond
    public List<Pond> getAllPonds() {
        return pondRepository.findAll();
    }

    // Lấy pond theo id
    public Optional<Pond> getPondById(UUID id) {
        return pondRepository.findById(id);
    }

    // Thêm mới pond
    public Pond addPond(Pond pond) {
        // Tạo UUID nếu chưa có
        if (pond.getId() == null) {
            pond.setId(UUID.randomUUID());
        }
        Pond savedPond = pondRepository.save(pond);

        // Tìm kiếm User và thêm Pond vào danh sách ponds
        Optional<User> userOptional = userRepository.findById(pond.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getPonds().add(savedPond); // Thêm Pond vào danh sách ponds của User
            userRepository.save(user); // Lưu lại User sau khi thêm Pond
        }

        return savedPond;

    }

    // Cập nhật pond theo id
    public Optional<Pond> updatePondById(UUID id, Pond pond) {
        return pondRepository.findById(id).map(existingPond -> {
            // Cập nhật các thuộc tính của pond
            existingPond.setPondName(pond.getPondName());
            existingPond.setVolume(pond.getVolume());
            existingPond.setDepth(pond.getDepth());
            existingPond.setDrainCount(pond.getDrainCount());
            existingPond.setSkimmerCount(pond.getSkimmerCount());
            existingPond.setPumpCapacity(pond.getPumpCapacity());
            existingPond.setImgUrl(pond.getImgUrl());
            existingPond.setStatus(pond.getStatus());
            existingPond.setQualified(pond.isQualified());
            existingPond.setUserId(pond.getUserId()); // Cập nhật userId nếu cần
            existingPond.setKoi(pond.getKoi()); // Cập nhật danh sách Koi
            existingPond.setMeasurements(pond.getMeasurements()); // Cập nhật danh sách Measurement

            return pondRepository.save(existingPond);
        });
    }

    // Xóa pond theo id
    public void deletePondById(UUID id) {
        pondRepository.deleteById(id);
    }
}
