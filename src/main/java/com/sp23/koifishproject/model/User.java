package com.sp23.koifishproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private UUID id;

    // Sử dụng DBRef để tham chiếu tới các hồ (Pond)
    @DBRef
    private List<Pond> ponds = new ArrayList<>();

    // Sử dụng DBRef để tham chiếu tới các đơn hàng (Order)
    @DBRef
    private List<Order> orders = new ArrayList<>();

    private String password;
    private String email;
    private String fullName;
    private Status status;
    private String phoneNumber;
    private Role role;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Role {
        ADMIN,
        SHOP,
        MEMBER
    }

    // Phương thức để tự động tạo UUID nếu chưa có
    public void generateIdIfAbsent() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
