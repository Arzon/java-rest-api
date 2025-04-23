package com.example.rest_product_api.service;

import com.example.rest_product_api.dto.AppUserDTO;
import com.example.rest_product_api.dto.ProductDTO;
import com.example.rest_product_api.entity.AppUser;
import com.example.rest_product_api.entity.Product;
import com.example.rest_product_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //Long id, String username, String password
    private AppUserDTO mapToDTO(AppUser user) {
        return new AppUserDTO(user.getId(),
                user.getUsername(),
                user.getUsername());
    }

    private AppUser mapToEntity(AppUserDTO userDTO) {
        return new AppUser(userDTO.getUsername(),
                userDTO.getPassword());
    }

    public void createUser(AppUserDTO userDTO) {
        AppUser user = mapToEntity(userDTO);
        userRepository.save(user);
    }

}
