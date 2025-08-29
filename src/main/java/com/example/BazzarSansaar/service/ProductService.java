package com.example.BazzarSansaar.service;

import com.example.BazzarSansaar.model.Product;
import com.example.BazzarSansaar.payload.ProductDto;
import com.example.BazzarSansaar.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) {
        Product product = mapToEntity(productDto);
        Product newProduct = productRepository.save(product);
        return mapToDto(newProduct);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            productDto.setCategoryName(product.getCategory().getName());
        }
        return productDto;
    }

    private Product mapToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        // Note: Category mapping would be more complex, handled separately
        return product;
    }
}
