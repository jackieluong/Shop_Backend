package com.example.shop.service;

import com.example.shop.dto.OrderDetailResponse;
import com.example.shop.dto.OrderRequest;
import com.example.shop.dto.UserOrderResponse;
import com.example.shop.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest orderRequest);

    OrderDetailResponse getOrderDetail(long id);

    Order updateOrder(OrderRequest orderRequest, long id);

    String cancelOrder(long id);

    List<Order> getAllOrders();

    List<UserOrderResponse> getUserOrders();
}
