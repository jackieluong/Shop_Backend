package com.example.shop.service;

import com.example.shop.dto.CartItem;
import com.example.shop.dto.CartRequest;

import java.util.List;

public interface CartService {
    String addToCart(CartRequest request);

    String updateQuantity(CartRequest request);

    String removeFromCart(CartRequest cartRequest);

    List<CartItem> getCartItems();
}
