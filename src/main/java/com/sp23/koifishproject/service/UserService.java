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
import java.util.ArrayList;
import com.sp23.koifishproject.config.JwtUtil;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    @Qualifier("mongoUserRepository")
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encryptedPasswordInDb = user.getPassword();

            // So sánh mật khẩu đã mã hóa từ DB với mật khẩu thô mà người dùng nhập vào
            if (passwordEncoder.matches(rawPassword, encryptedPasswordInDb)) {
                return Optional.of(user);  // Trả về đối tượng user nếu đăng nhập thành công
            }
        }
        return Optional.empty();  // Trả về empty nếu thất bại
    }

    // Phương thức tạo token dựa trên email và role của user
    public String generateToken(User user) {
        String role = user.getRole().name();
        return jwtUtil.generateToken(user.getEmail(), role);
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

    // Thêm người dùng mới
    public User addUser(User user) {
        // Kiểm tra email đã tồn tại
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with email already exists");
        }

        // Generate UUID nếu chưa có
        user.generateIdIfAbsent();

        // Kiểm tra nếu danh sách ponds là null, khởi tạo mảng rỗng
        if (user.getPonds() == null) {
            user.setPonds(new ArrayList<>());  // Thiết lập danh sách ponds là mảng rỗng nếu null
        }

        // Kiểm tra nếu danh sách orders là null, khởi tạo mảng rỗng
        if (user.getOrders() == null) {
            user.setOrders(new ArrayList<>());  // Thiết lập danh sách orders là mảng rỗng nếu null
        }

        // Mã hóa mật khẩu
        System.out.println("Raw password before encoding: " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Encrypted password after encoding: " + user.getPassword());

        return userRepository.save(user);
    }

    // Xóa người dùng theo ID
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    // Cập nhật người dùng theo ID
    public Optional<User> updateUserById(UUID id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            // Nếu mật khẩu không null, mã hóa mật khẩu mới
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Cập nhật các thuộc tính khác
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
