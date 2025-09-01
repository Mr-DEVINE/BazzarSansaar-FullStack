package com.example.BazzarSansaar.payload;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private String imageUrl;
}
