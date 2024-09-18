package com.sp23.koifishproject.controller;

import com.sp23.koifishproject.model.User;
import com.sp23.koifishproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/koi/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}
