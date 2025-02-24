package com.example.shop.repository;

import com.example.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c " +
            "JOIN FETCH c.cartProducts cp " +
            "JOIN FETCH cp.product " +
            "WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithProducts(@Param("userId") Long userId);
}
