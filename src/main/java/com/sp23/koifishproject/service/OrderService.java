package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Order;
import com.sp23.koifishproject.model.OrderDetail;
import com.sp23.koifishproject.model.Product;
import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.repository.mongo.OrderDetailRepository;
import com.sp23.koifishproject.repository.mongo.OrderRepository;
import com.sp23.koifishproject.repository.mongo.ProductRepository;
import com.sp23.koifishproject.repository.mongo.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
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
        // Tạo UUID cho order nếu chưa có
        order.setId(UUID.randomUUID());

        // Nạp đầy đủ thông tin chi tiết từ các OrderDetail
        List<OrderDetail> populatedOrderDetails = order.getOrderDetails().stream()
                .map(orderDetail -> orderDetailRepository.findById(orderDetail.getId())
                        .orElseThrow(() -> new IllegalArgumentException("OrderDetail not found")))
                .collect(Collectors.toList());

        order.setOrderDetails(populatedOrderDetails);

        // Tính tổng giá trị của đơn hàng
        order.calculateTotalPrice();

        // Lưu Order
        Order savedOrder = orderRepository.save(order);

        // Tìm User bằng idUser từ Order
        Optional<User> userOptional = userRepository.findById(order.getIdUser());
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Thêm ID của Order vào danh sách orders của User
            user.getOrders().add(savedOrder);

            // Lưu User sau khi cập nhật
            userRepository.save(user);
        }

        return savedOrder;
    }

    // Cập nhật đơn hàng theo ID và xử lý khi trạng thái là PAID
    public Optional<Order> updateOrderById(UUID id, Order orderDetails) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setOrderDetails(orderDetails.getOrderDetails());
            existingOrder.calculateTotalPrice();

            // Nếu trạng thái là PAID, trừ số lượng sản phẩm
            if (orderDetails.getStatus() == Order.Status.PAID) {
                for (OrderDetail orderDetail : existingOrder.getOrderDetails()) {
                    Product product = orderDetail.getProduct();
                    int remainingQuantity = product.getQuantity() - orderDetail.getQuantity();

                    if (remainingQuantity < 0) {
                        throw new IllegalStateException("Not enough stock for product: " + product.getProductName());
                    }

                    product.setQuantity(remainingQuantity);
                    productRepository.save(product);
                }
            }

            existingOrder.setStatus(orderDetails.getStatus());
            return orderRepository.save(existingOrder);
        });
    }
    public Optional<Order> updateOrderStatus(UUID id, Order.Status status) {
        return orderRepository.findById(id).map(existingOrder -> {

            // Nếu trạng thái là PAID, trừ số lượng sản phẩm
            if (status == Order.Status.PAID) {
                for (OrderDetail orderDetail : existingOrder.getOrderDetails()) {
                    Product product = orderDetail.getProduct();
                    int remainingQuantity = product.getQuantity() - orderDetail.getQuantity();

                    if (remainingQuantity < 0) {
                        throw new IllegalStateException("Not enough stock for product: " + product.getProductName());
                    }

                    product.setQuantity(remainingQuantity);
                    productRepository.save(product);
                }
            }

            // Cập nhật trạng thái của đơn hàng
            existingOrder.setStatus(status);
            return orderRepository.save(existingOrder);
        });
    }


    // Xóa đơn hàng theo ID
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }
}
