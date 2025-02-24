package com.example.shop.repository;

import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

//    Optional<User> findByUsernameOrEmail(String username, String email);

//    Optional<User> findByUsername(String username);

//    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    @Query("SELECT u FROM User u JOIN FETCH u.cart WHERE u.email = :email")
//Optional<User> findByEmailWithCart(@Param("email") String email);

    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.cart c
        JOIN FETCH c.cartProducts cp
        JOIN FETCH cp.product
        WHERE u.email = :email
    """)
    Optional<User> findByEmailWithCartAndProducts(@Param("email") String email);


//    Optional<User> findByEmailAndRefreshToken(String email, String refreshToken);
}
