package com.example.shop.repository;

import com.example.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT o FROM Order o
    JOIN FETCH o.user
    JOIN FETCH o.orderProducts op
    JOIN FETCH op.product
    WHERE o.id = :orderId
""")
    Optional<Order> findOrderWithAllDetails(@Param("orderId") Long orderId);

    @Query("""
    SELECT o FROM Order o
    JOIN FETCH o.orderProducts op
    JOIN FETCH op.product p
    WHERE o.user.email = :email
""")
    List<Order> findOrdersWithProductsByUserEmail(@Param("email") String email);
}
