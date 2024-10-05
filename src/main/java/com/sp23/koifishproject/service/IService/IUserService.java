package com.sp23.koifishproject.service.IService;

import com.sp23.koifishproject.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    String login(String email, String rawPassword);
    UserDetails loadUserByUsername(String email);
    List<User> getAllUsers();
    Optional<User> getUserById(UUID id);
    User addUser(User user);
    void deleteUserById(UUID id);
    Optional<User> updateUserById(UUID id, User user);
}
