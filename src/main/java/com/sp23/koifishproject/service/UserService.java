package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.repository.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sp23.koifishproject.config.JwtUtil;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Qualifier("mongoUserRepository")
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encryptedPasswordInDb = user.getPassword();  // Mật khẩu mã hóa trong DB

            // In ra mật khẩu đã mã hóa từ DB
            System.out.println("Encrypted password in DB: " + encryptedPasswordInDb);

            // So sánh mật khẩu đã mã hóa từ DB với mật khẩu thô mà người dùng nhập vào
            if (passwordEncoder.matches(rawPassword, encryptedPasswordInDb)) {
                // Lấy role của người dùng và chuyển đổi thành chuỗi
                String role = user.getRole().name();  // Sử dụng name() nếu Role là enum
                // Truyền role vào JWT token
                String token = jwtUtil.generateToken(user.getEmail(), role);
                System.out.println("Password matches, JWT generated: " + token);  // Log khi mật khẩu đúng và JWT được tạo
                return token;
            } else {
                System.out.println("Password does not match for user: " + email);  // Log khi mật khẩu không khớp
            }

        } else {
            System.out.println("No user found with email: " + email);  // Log khi không tìm thấy user
        }
        return null; // Đăng nhập thất bại
    }









    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy người dùng theo ID
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        // Kiểm tra email đã tồn tại hay chưa
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with email already exists");
        }
        user.generateIdIfAbsent();
        // In ra mật khẩu trước khi mã hóa để kiểm tra
        System.out.println("Raw password before encoding: " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // In ra mật khẩu sau khi mã hóa để kiểm tra
        System.out.println("Encrypted password after encoding: " + user.getPassword());

        return userRepository.save(user);
    }


    // Xóa người dùng theo ID
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    // Cập nhật người dùng theo ID (không thay đổi mật khẩu nếu không được truyền)
    public Optional<User> updateUserById(UUID id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            // Nếu mật khẩu không null, mã hóa mật khẩu mới
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Cập nhật các thuộc tính khác, bỏ qua những thuộc tính như ID
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getFullName() != null) {
                existingUser.setFullName(user.getFullName());
            }
            if (user.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(user.getPhoneNumber());
            }
            if (user.getStatus() != null) {
                existingUser.setStatus(user.getStatus());
            }
            if (user.getRole() != null) {
                existingUser.setRole(user.getRole());
            }

            return userRepository.save(existingUser);
        });
    }
}
