package com.sp23.koifishproject.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private UUID id;

    private List<Pond> ponds;
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
