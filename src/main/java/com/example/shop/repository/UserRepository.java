package com.example.shop.repository;

import com.example.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

//    Optional<User> findByUsernameOrEmail(String username, String email);

//    Optional<User> findByUsername(String username);

//    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    Optional<User> findByEmailAndRefreshToken(String email, String refreshToken);
}
