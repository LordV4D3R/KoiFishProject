package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.OrderDetail;
import com.sp23.koifishproject.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // Lấy tất cả chi tiết đơn hàng
    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        return ResponseEntity.ok(orderDetails);
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderDetailById(@PathVariable UUID id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        return orderDetail.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("OrderDetail not found"));
    }

    // Tạo OrderDetail mới
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetail);
            return ResponseEntity.ok(createdOrderDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Cập nhật chi tiết đơn hàng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrderDetailById(@PathVariable UUID id, @RequestBody OrderDetail orderDetail) {
        Optional<OrderDetail> updatedOrderDetail = orderDetailService.updateOrderDetailById(id, orderDetail);
        return updatedOrderDetail.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("OrderDetail not found"));
    }

    // Xóa chi tiết đơn hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetailById(@PathVariable UUID id) {
        Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail.isPresent()) {
            orderDetailService.deleteOrderDetailById(id);
            return ResponseEntity.status(204).body("OrderDetail deleted successfully");
        } else {
            return ResponseEntity.status(404).body("OrderDetail not found");
        }
    }
}
