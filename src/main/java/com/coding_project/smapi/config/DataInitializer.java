package com.coding_project.smapi.config;

import com.coding_project.smapi.entity.User;
import com.coding_project.smapi.enums.Role;
import com.coding_project.smapi.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("ajurje") == null) {
            User admin = new User();
            admin.setUsername("ajurje");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setFirstName("Andreea");
            admin.setLastName("Jurje");
            admin.setEmail("andreea.oprean@ibm.com");
            admin.setPhone("+40740284013");
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
}
