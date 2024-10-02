package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.dto.LoginRequest;
import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (token != null) {
            System.out.println("JWT Token: " + token);  // Log JWT token sau khi login
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }



    // Lấy danh sách tất cả người dùng
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Lấy thông tin người dùng theo id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        return user.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User newUser = userService.addUser(user);
            return ResponseEntity.status(201).body(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());  // Trả về thông báo lỗi "User with email already exists"
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error adding user: " + e.getMessage());
        }
    }


    // Xóa người dùng theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.status(204).body("User deleted successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Cập nhật thông tin người dùng theo id
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable UUID id, @RequestBody User user) {
        Optional<User> updatedUser = userService.updateUserById(id, user);
        return updatedUser.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }

}
