package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.Product;
import com.sp23.koifishproject.repository.mongo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    // Thêm mới sản phẩm
    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm theo ID
    public Optional<Product> updateProductById(UUID id, Product productDetails) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(productDetails.getProductName());
            existingProduct.setProductPrice(productDetails.getProductPrice());
            existingProduct.setQuantity(productDetails.getQuantity());
            existingProduct.setStatus(productDetails.getStatus());
            return productRepository.save(existingProduct);
        });
    }

    // Xóa sản phẩm theo ID
    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }
}
