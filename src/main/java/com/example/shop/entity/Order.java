package com.example.shop.entity;

import com.example.shop.enums.OrderStatusEnum;
import com.example.shop.enums.PaymentMethodEnum;
import com.example.shop.enums.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AppOrder")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "ship_fee")
    private double shipFee;

    @Column(name = "status")
    private OrderStatusEnum orderStatus;

    @Column(name = "payment_status")
    private PaymentStatusEnum paymentStatus;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderProduct> orderProducts;


}
