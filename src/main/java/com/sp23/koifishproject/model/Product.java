package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private UUID id;

    private String productName;
    private double productPrice;
    private int quantity;
    private String status;
}
