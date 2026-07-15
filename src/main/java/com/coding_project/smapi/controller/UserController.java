package com.coding_project.smapi.controller;

import com.coding_project.smapi.model.User;
import com.coding_project.smapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    private String login(@RequestBody User user) {
        return userService.login(user);
    }
}
