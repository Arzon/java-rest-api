package com.example.rest_product_api.service;
import com.example.rest_product_api.entity.User;
import com.example.rest_product_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerUserDetailsService implements UserDetailsService  {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = userRepository.findUserByUsername(username);

        if(userInfo == null) {
            throw new UsernameNotFoundException("User not Found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(),
                userInfo.getPassword(),
                Collections.emptyList()
        );
    }
}
