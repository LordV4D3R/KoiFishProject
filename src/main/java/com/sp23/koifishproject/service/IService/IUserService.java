package com.sp23.koifishproject.service.IService;

import com.sp23.koifishproject.model.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User addUser(User user);
    void deleteUserById(UUID id);
    User updateUserById(UUID id, User user);
}
