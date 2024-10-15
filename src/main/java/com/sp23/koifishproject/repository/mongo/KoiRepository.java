package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.Koi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import java.util.UUID;

@Repository("KoiRepository")
@Qualifier("KoiRepository")
public interface KoiRepository extends MongoRepository<Koi, UUID> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
