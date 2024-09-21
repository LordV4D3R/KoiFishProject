package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("mongoUserRepository")
@Qualifier("mongoUserRepository")
public interface UserRepository extends MongoRepository<User, UUID> {
}
