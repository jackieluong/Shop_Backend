package com.example.shop.controller;

import com.example.shop.dto.*;
import com.example.shop.entity.Order;
import com.example.shop.entity.Product;
import com.example.shop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createOrder(@Valid @RequestBody OrderRequest orderRequest) {

        Order createdOrder = orderService.createOrder(orderRequest);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Create order successfully")
                .setData(createdOrder)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOrderDetail(@PathVariable(name = "id") Long id) {

        OrderDetailResponse order = orderService.getOrderDetail(id);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Get order successfully")
                .setData(order)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Get all orders successfully")
                .setData(orders)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDto> updateOrder(@Valid @RequestBody OrderRequest orderRequest, @PathVariable(name = "id") Long id) {

        Order updatedOrder = orderService.updateOrder(orderRequest, id);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Update order successfully")
                .setData(updatedOrder)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> getUserOrders() {

        List<UserOrderResponse> userOrders = orderService.getUserOrders();

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Get user orders successfully")
                .setData(userOrders)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }



}
