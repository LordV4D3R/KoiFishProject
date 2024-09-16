package com.sp23.koifishproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    private @Id @GeneratedValue UUID id;

    @OneToMany(mappedBy = "user")
    private List<Pond> ponds;

    private String password;
    private String email;
    private String fullName;
    private status status;
    private String phoneNumber;
    private role role;

    public enum status {
        ACTIVE,
        INACTIVE
    }
    public enum role {
        ADMIN,
        SHOP,
        MEMBER
    }

}
