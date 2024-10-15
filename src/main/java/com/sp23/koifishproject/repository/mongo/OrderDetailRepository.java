package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Repository("OrderDetailRepository")
@Qualifier("OrderDetailRepository")
public interface OrderDetailRepository extends MongoRepository<OrderDetail, UUID> {
}
