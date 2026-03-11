package com.example.unitprojectspring.Service;
import com.example.unitprojectspring.Entities.User;
import com.example.unitprojectspring.Repositories.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@NullMarked
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginInput) throws UsernameNotFoundException {

        // Pass the input into both the username and email parameters
        User user = userRepository.findByUsernameOrEmail(loginInput, loginInput)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username/email: " + loginInput));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    //DO NOT ADD TO THIS, ONLY FOR LOGIN
}