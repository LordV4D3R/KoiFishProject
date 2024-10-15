package com.sp23.koifishproject.repository.mongo;

import com.sp23.koifishproject.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

@Repository("ProductRepository")
@Qualifier("ProductRepository")
public interface ProductRepository extends MongoRepository<Product, UUID> {
}
