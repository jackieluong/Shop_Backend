package com.example.shop.repository;

import com.example.shop.entity.OrderProduct;
import com.example.shop.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
