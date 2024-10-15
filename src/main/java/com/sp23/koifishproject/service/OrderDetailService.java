package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.OrderDetail;
import com.sp23.koifishproject.model.Product;
import com.sp23.koifishproject.repository.mongo.OrderDetailRepository;
import com.sp23.koifishproject.repository.mongo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả chi tiết đơn hàng
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    // Lấy chi tiết đơn hàng theo ID
    public Optional<OrderDetail> getOrderDetailById(UUID id) {
        return orderDetailRepository.findById(id);
    }

    // Tạo OrderDetail mới
    public OrderDetail createOrderDetail(OrderDetail newOrderDetail) {
        // Lấy sản phẩm từ productId
        Optional<Product> productOptional = productRepository.findById(newOrderDetail.getProduct().getId());
        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();

        // Gán thông tin chi tiết của sản phẩm vào OrderDetail
        newOrderDetail.setProduct(product);  // Lưu toàn bộ thông tin của product vào OrderDetail

        // Tính giá của OrderDetail = productPrice * quantity
        double priceOrderDetail = product.getProductPrice() * newOrderDetail.getQuantity();
        newOrderDetail.setPriceOrderDetail(priceOrderDetail);

        // Tạo UUID cho OrderDetail
        newOrderDetail.setId(UUID.randomUUID());

        return orderDetailRepository.save(newOrderDetail);
    }

    // Cập nhật chi tiết đơn hàng
    public Optional<OrderDetail> updateOrderDetailById(UUID id, OrderDetail orderDetailDetails) {
        return orderDetailRepository.findById(id).map(existingOrderDetail -> {
            existingOrderDetail.setProduct(orderDetailDetails.getProduct());
            existingOrderDetail.setQuantity(orderDetailDetails.getQuantity());
            existingOrderDetail.setPriceOrderDetail(orderDetailDetails.getPriceOrderDetail());
            return orderDetailRepository.save(existingOrderDetail);
        });
    }

    // Xóa chi tiết đơn hàng theo ID
    public void deleteOrderDetailById(UUID id) {
        orderDetailRepository.deleteById(id);
    }
}
