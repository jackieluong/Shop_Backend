package com.example.shop.service.impl;

import com.example.shop.dto.CartItem;
import com.example.shop.dto.CartRequest;
import com.example.shop.entity.*;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.JwtTokenProvider;
import com.example.shop.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    public CartServiceImpl(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addToCart(CartRequest request) {
        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("No user was found"));

        User curUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


//        Cart cart = curUser.getCart();
//        if(cart == null){
//            cart = new Cart();
//            cart.setUser(curUser);
//        }


        Cart cart = cartRepository.findByUserIdWithProducts((long) curUser.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(curUser);
                    return newCart;
                });

        Product product =  productRepository.findById(request.getProductID())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductID()));


       CartProduct cartProduct = cart.getCartProducts().stream()
                        .filter(cp -> cp.getProduct().getId() == product.getId())
                        .findFirst()
                        .orElse(null);

       if(cartProduct != null){
//           Update quantity if product already in cart
            cartProduct.setQuantity(request.getQuantity() + cartProduct.getQuantity());
       }else {
           // Add new product to cart
           CartProductId cartProductId = new CartProductId(cart.getId(), product.getId());
           cartProduct = new CartProduct(cartProductId, cart, product, request.getQuantity());
           cart.addProductToCart(cartProduct);
       }


        cartRepository.save(cart);

        return "Add to Cart successfully";
    }

    @Override
    public String updateQuantity(CartRequest cartRequest) {
        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("No user was found"));

        User curUser = userRepository.findByEmailWithCartAndProducts(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = curUser.getCart();

        if(cart == null) {
            throw new RuntimeException("Can not find cart for user");
        }

        CartProduct cartProduct = cart.getCartProducts().stream().
                filter(cp -> cp.getProduct().getId() == cartRequest.getProductID())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", cartRequest.getProductID()));

        cartProduct.setQuantity(cartProduct.getQuantity() + cartRequest.getQuantity());

        cartRepository.save(cart);

        return "Update quantity successfully";
    }

    @Override
    public String removeFromCart(CartRequest cartRequest) {
        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("No user was found"));

        User curUser = userRepository.findByEmailWithCartAndProducts(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = curUser.getCart();

        boolean removed =  cart.getCartProducts()
                .removeIf(cp -> cp.getProduct().getId() == cartRequest.getProductID());

        if(!removed) {
            throw new RuntimeException("Can not remove product from cart");
        }

        cartRepository.save(cart);
        return "Remove from cart successfully";
    }

    @Override
    public List<CartItem> getCartItems() {
        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("No user was found"));

        User curUser = userRepository.findByEmailWithCartAndProducts(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = curUser.getCart();
        List<CartItem> cartItems = cart.getCartProducts().stream().map(cartProduct -> {
            CartItem.Product product = modelMapper.map(cartProduct.getProduct(), CartItem.Product.class) ;

            return new CartItem(cartProduct.getQuantity(), product);
        }
        ).collect(Collectors.toList());

        return cartItems;
    }


}
