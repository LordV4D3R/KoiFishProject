package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Order;
import com.sp23.koifishproject.model.OrderDetail;
import com.sp23.koifishproject.repository.mongo.OrderDetailRepository;
import com.sp23.koifishproject.repository.mongo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy đơn hàng theo ID
    public Optional<Order> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    // Tạo đơn hàng mới với danh sách OrderDetail
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID());

        // Nạp đầy đủ thông tin chi tiết từ các OrderDetail
        List<OrderDetail> populatedOrderDetails = order.getOrderDetails().stream()
                .map(orderDetail -> orderDetailRepository.findById(orderDetail.getId())
                        .orElseThrow(() -> new IllegalArgumentException("OrderDetail not found")))
                .collect(Collectors.toList());

        order.setOrderDetails(populatedOrderDetails);

        // Tính tổng giá trị của đơn hàng
        order.calculateTotalPrice();

        return orderRepository.save(order);
    }

    // Cập nhật đơn hàng theo ID
    public Optional<Order> updateOrderById(UUID id, Order orderDetails) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setOrderDetails(orderDetails.getOrderDetails());
            existingOrder.calculateTotalPrice();
            return orderRepository.save(existingOrder);
        });
    }

    // Xóa đơn hàng theo ID
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }
}
