package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private UUID id;

    // Many-to-One relationship with User
    private UUID idUser;

    // One-to-Many relationship with OrderDetail
    @DBRef
    private List<OrderDetail> orderDetails;

    private double totalPrice;

    // Thêm thuộc tính trạng thái
    private Status status;

    public enum Status {
        PENDING,
        PAID,
        CANCELED
    }

    public void calculateTotalPrice() {
        this.totalPrice = orderDetails.stream()
                .mapToDouble(OrderDetail::getPriceOrderDetail)
                .sum();
    }
}

