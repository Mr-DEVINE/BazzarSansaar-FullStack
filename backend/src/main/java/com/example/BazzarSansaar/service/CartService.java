package com.example.BazzarSansaar.service;

import com.example.BazzarSansaar.model.Cart;
import com.example.BazzarSansaar.model.CartItem;
import com.example.BazzarSansaar.model.Product;
import com.example.BazzarSansaar.model.User;
import com.example.BazzarSansaar.payload.CartDto;
import com.example.BazzarSansaar.payload.CartItemDto;
import com.example.BazzarSansaar.repository.CartRepository;
import com.example.BazzarSansaar.repository.ProductRepository;
import com.example.BazzarSansaar.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartDto addItemToCart(String username, Long productId, int quantity) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Cart cart = cartRepository.findByUserUsername(username)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        // Check if item is already in cart
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                updateCartTotal(cart);
                return mapToDto(cartRepository.save(cart));
            }
        }

        // If not in cart, add as new item
        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(quantity);
        cart.getCartItems().add(newCartItem);

        updateCartTotal(cart);
        Cart savedCart = cartRepository.save(cart);
        return mapToDto(savedCart);
    }

    @Transactional(readOnly = true)
    public CartDto getCartByUsername(String username) {
        Cart cart = cartRepository.findByUserUsername(username).orElse(null);
        if (cart == null) {
            // Optionally create a cart if one doesn't exist, or return null/empty
            return null;
        }
        return mapToDto(cart);
    }


    private void updateCartTotal(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(total);
    }

    private CartDto mapToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setCartItems(cart.getCartItems().stream().map(this::mapToItemDto).collect(Collectors.toList()));
        return cartDto;
    }

    private CartItemDto mapToItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setImageUrl(cartItem.getProduct().getImageUrl());
        return dto;
    }
}

