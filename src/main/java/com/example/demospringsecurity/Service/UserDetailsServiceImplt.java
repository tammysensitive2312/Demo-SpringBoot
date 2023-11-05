package com.example.demospringsecurity.Service;

import com.example.demospringsecurity.Config.UserDetailsImplt;
import com.example.demospringsecurity.Entity.User;
import com.example.demospringsecurity.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImplt implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        return user.map(UserDetailsImplt::new)
                .orElseThrow(() -> new UsernameNotFoundException("not found" + username));
    }
}
