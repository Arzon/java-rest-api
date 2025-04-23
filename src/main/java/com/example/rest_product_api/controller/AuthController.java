package com.example.rest_product_api.controller;

import com.example.rest_product_api.dto.AppUserDTO;
import com.example.rest_product_api.repository.UserRepository;
import com.example.rest_product_api.security.JwtUtil;
import com.example.rest_product_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody AppUserDTO user) {
        Authentication authentication = authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(
                   user.getUsername(),
                   user.getPassword()
           )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody AppUserDTO user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken";
        }

        AppUserDTO newUser = new AppUserDTO(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword())
        );

        userService.createUser(newUser);
        return "User registered successfully";
    }
}
