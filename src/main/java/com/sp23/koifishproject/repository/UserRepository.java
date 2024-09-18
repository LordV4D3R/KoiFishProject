package com.sp23.koifishproject.repository;

import com.sp23.koifishproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
