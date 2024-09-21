package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.Pond;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("pondRepository")
@Qualifier("pondRepository")
public interface PondRepository extends MongoRepository<Pond, UUID> {
}
