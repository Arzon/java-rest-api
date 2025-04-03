package com.example.rest_product_api.service;

import com.example.rest_product_api.dto.ProductDTO;

import com.example.rest_product_api.entity.Product;
import com.example.rest_product_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //Convert Product entity to ProductDTO
    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());
    }

    //Convert ProductDTO to Product Entity
    private Product mapToEntity(ProductDTO productDTO) {
        return new Product(productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice());
    }

    //Get all products
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO).collect(Collectors.toList());
    }

    //Get a single product by ID
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Product not found") );
        return mapToDTO(product);
    }

    //create a new product
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = mapToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    //update an existing product
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Product not found"));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        Product updateProduct = productRepository.save(existingProduct);
        return mapToDTO(updateProduct);
    }

    //Delete a product
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

}
