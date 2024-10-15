package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Repository("OrderRepository")
@Qualifier("OrderRepository")
public interface OrderRepository extends MongoRepository<Order, UUID> {
}
