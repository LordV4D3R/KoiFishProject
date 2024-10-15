package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order_details")
public class OrderDetail {
    @Id
    private UUID id;

    // Many-to-One relationship with Product
    @DBRef
    private Product product;

    // Số lượng của sản phẩm
    private int quantity;

    // Giá của OrderDetail = productPrice * quantity
    private double priceOrderDetail;
}
