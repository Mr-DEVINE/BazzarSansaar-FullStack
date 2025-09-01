package com.example.BazzarSansaar.payload;

import lombok.Data;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private List<CartItemDto> cartItems;
    private double totalPrice;
}
