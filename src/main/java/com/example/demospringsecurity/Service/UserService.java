package com.example.demospringsecurity.Service;

import com.example.demospringsecurity.Entity.User;
import com.example.demospringsecurity.Repository.UserRepository;
import com.example.demospringsecurity.Common.UserConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public String addNewUser(User user ) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user added to system";
    }

    public String assignRoleToUser(Long id, String role, String principalName) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<String> activeRoles = getRolesByUserName(principalName);

        if (activeRoles.contains(role)) {
            String newRole =user.getRole() + "," + role;
            user.setRole(newRole);
            userRepository.save(user);
        }
        return "Hi" + user.getName() + "new role assigned to you by" + principalName;
        }

    private List<String> getRolesByUserName(String principalName) {
        User user = userRepository.findByName(principalName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String roles = user.getRole();
        List<String> assignRole = Arrays
                .stream(roles.split(","))
                .collect(Collectors.toList());

        if (assignRole.contains("ROLE_ADMIN")) {
            return Arrays.asList(UserConstant.ADMIN_ACCESS);
        }
        if (assignRole.contains("ROLE_MODERATOR")) {
            return Arrays.asList(UserConstant.MODERATOR_ACCESS);
        }

        return Collections.emptyList();
    }
}

