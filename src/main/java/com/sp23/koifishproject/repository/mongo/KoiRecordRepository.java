package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.KoiRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Repository("KoiRecordRepository")
@Qualifier("KoiRecordRepository")
public interface KoiRecordRepository extends MongoRepository<KoiRecord, UUID> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
