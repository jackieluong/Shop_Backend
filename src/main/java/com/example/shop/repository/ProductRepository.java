package com.example.shop.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.feedBacks WHERE p.id = :id")
//    Optional<Product> findByIdWithFeedbacks(@Param("id") Long id);

//    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.feedBacks fb LEFT JOIN FETCH fb.user WHERE p.id = :id")
//    Optional<Product> findByIdWithFeedbacks(@Param("id") Long id);
//
//    @Query("SELECT p FROM Product p WHERE p.id = :id")
//    Optional<Product> findSimpleProductById(@Param("id") Long id);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.feedBacks fb LEFT JOIN FETCH fb.user WHERE p.id = :id")
    Optional<Product> findProductWithFeedbacksAndUsersById(@Param("id") Long id);

//    @EntityGraph(attributePaths = {"feedBacks.user"})
//    Optional<Product> findById(Long id);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);
}
