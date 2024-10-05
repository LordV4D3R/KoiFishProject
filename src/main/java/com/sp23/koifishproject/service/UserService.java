package com.sp23.koifishproject.service;

import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.repository.mongo.UserRepository;
import com.sp23.koifishproject.service.IService.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    @Qualifier("mongoUserRepository")
    private UserRepository userRepository;

    //Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Get user by id
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    //Add new user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //Delete user by id
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);

    }

    //Update user by id
    public User updateUserById(UUID id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            BeanUtils.copyProperties(user, existingUser, "id");
            return userRepository.save(existingUser);
        }).orElse(null);
    }
}
