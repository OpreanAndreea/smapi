package com.coding_project.smapi.service;

import com.coding_project.smapi.model.User;
import com.coding_project.smapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(User user){
        User dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser == null){
            return "User not found";
        }
        else {
            if(dbUser.getPassword().equals(user.getPassword())){
                return "Logged in successfully as: " + user.getUsername();
            }
            else {
                return "Wrong password";
            }
        }
    }
}
