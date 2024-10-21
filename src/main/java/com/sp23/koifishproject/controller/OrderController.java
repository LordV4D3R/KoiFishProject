package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.dto.UpdateOrderStatusDTO;
import com.sp23.koifishproject.model.Order;
import com.sp23.koifishproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Tạo đơn hàng mới
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Order created successfully");
            response.put("data", createdOrder);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable UUID id) {
        try {
            Optional<Order> order = orderService.getOrderById(id);
            if (order.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("data", order.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Order not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve order: " + e.getMessage()));
        }
    }

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", orders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to retrieve orders: " + e.getMessage()));
        }
    }

    // Cập nhật đơn hàng theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderById(@PathVariable UUID id, @RequestBody Order order) {
        try {
            Optional<Order> updatedOrder = orderService.updateOrderById(id, order);
            if (updatedOrder.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Order updated successfully");
                response.put("data", updatedOrder.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Order not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update order: " + e.getMessage()));
        }
    }

    // Cập nhật trạng thái đơn hàng
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable UUID id, @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            Order.Status status = updateOrderStatusDTO.getStatus();
            Optional<Order> updatedOrder = orderService.updateOrderStatus(id, status);

            if (updatedOrder.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Order status updated successfully");
                response.put("data", updatedOrder.get());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Order not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to update order status: " + e.getMessage()));
        }
    }

    // Xóa đơn hàng theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable UUID id) {
        try {
            Optional<Order> order = orderService.getOrderById(id);
            if (order.isPresent()) {
                orderService.deleteOrderById(id);
                return ResponseEntity.status(204)
                        .body(Collections.singletonMap("status", "Order deleted successfully"));
            } else {
                return ResponseEntity.status(404)
                        .body(Collections.singletonMap("error", "Order not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("error", "Failed to delete order: " + e.getMessage()));
        }
    }
}
