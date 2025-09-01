package com.example.BazzarSansaar.repository;

import com.example.BazzarSansaar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

