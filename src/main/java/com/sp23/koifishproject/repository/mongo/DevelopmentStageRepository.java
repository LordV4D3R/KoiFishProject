package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.DevelopmentStage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.UUID;

@Repository("DevelopmentStageRepository")
@Qualifier("DevelopmentStageRepository")
public interface DevelopmentStageRepository extends MongoRepository<DevelopmentStage, UUID> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}