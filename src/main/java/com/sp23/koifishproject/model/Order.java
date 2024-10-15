package com.sp23.koifishproject.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private UUID idUser;  // Liên kết với người dùng

    // One-to-Many relationship with OrderDetail
    @DBRef
    private List<OrderDetail> orderDetails;

    // Tổng giá trị của đơn hàng
    private double totalPrice;

    // Tính tổng số tiền dựa trên các OrderDetail
    public void calculateTotalPrice() {
        this.totalPrice = orderDetails.stream()
                .mapToDouble(OrderDetail::getPriceOrderDetail)
                .sum();
    }
}
