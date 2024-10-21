package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.OrderDetail;
import com.sp23.koifishproject.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    // Lấy tất cả chi tiết đơn hàng
    @GetMapping
    public ResponseEntity<?> getAllOrderDetails() {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", orderDetails);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve order details: " + e.getMessage()));
        }
    }

    // Lấy chi tiết đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@PathVariable UUID id) {
        try {
            Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
            if (orderDetail.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", orderDetail.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "OrderDetail not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve order detail: " + e.getMessage()));
        }
    }

    // Tạo OrderDetail mới
    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail createdOrderDetail = orderDetailService.createOrderDetail(orderDetail);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "OrderDetail created successfully");
            response.put("data", createdOrderDetail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Error creating order detail: " + e.getMessage()));
        }
    }

    // Cập nhật chi tiết đơn hàng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetailById(@PathVariable UUID id, @RequestBody OrderDetail orderDetail) {
        try {
            Optional<OrderDetail> updatedOrderDetail = orderDetailService.updateOrderDetailById(id, orderDetail);
            if (updatedOrderDetail.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "OrderDetail updated successfully");
                response.put("data", updatedOrderDetail.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "OrderDetail not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update order detail: " + e.getMessage()));
        }
    }

    // Xóa chi tiết đơn hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetailById(@PathVariable UUID id) {
        try {
            Optional<OrderDetail> orderDetail = orderDetailService.getOrderDetailById(id);
            if (orderDetail.isPresent()) {
                orderDetailService.deleteOrderDetailById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "OrderDetail deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "OrderDetail not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete order detail: " + e.getMessage()));
        }
    }
}
