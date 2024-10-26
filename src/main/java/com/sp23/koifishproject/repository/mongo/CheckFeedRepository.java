package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.CheckFeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface CheckFeedRepository extends MongoRepository<CheckFeed, UUID> {
}
