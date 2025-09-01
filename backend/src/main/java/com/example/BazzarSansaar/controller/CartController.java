package com.example.BazzarSansaar.controller;

import com.example.BazzarSansaar.payload.CartDto;
import com.example.BazzarSansaar.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> addItemToCart(@RequestParam Long productId, @RequestParam int quantity, Principal principal) {
        CartDto updatedCart = cartService.addItemToCart(principal.getName(), productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> getUserCart(Principal principal) {
        CartDto cartDto = cartService.getCartByUsername(principal.getName());
        if (cartDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartDto);
    }
}

