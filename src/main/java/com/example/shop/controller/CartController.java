package com.example.shop.controller;

import com.example.shop.dto.CartItem;
import com.example.shop.dto.CartRequest;
import com.example.shop.dto.ResponseDto;
import com.example.shop.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> addToCart(@Valid @RequestBody CartRequest cartRequest) {

        cartService.addToCart(cartRequest);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Add to cart successfully")
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseDto> updateCart(@Valid @RequestBody CartRequest cartRequest) {

        cartService.updateQuantity(cartRequest);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Update quantity successfully")
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> removeFromCart(@Valid @RequestBody CartRequest cartRequest) {

        String message = cartService.removeFromCart(cartRequest);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage(message)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getCartItems() {

        List<CartItem> cartItems = cartService.getCartItems();

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("OK")
                .setData(cartItems)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
