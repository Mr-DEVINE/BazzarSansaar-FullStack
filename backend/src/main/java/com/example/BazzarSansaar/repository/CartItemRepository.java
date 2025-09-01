package com.example.BazzarSansaar.repository;

import com.example.BazzarSansaar.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

