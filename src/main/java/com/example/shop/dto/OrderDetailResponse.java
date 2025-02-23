package com.example.shop.dto;

import com.example.shop.constant.OrderStatusEnum;
import com.example.shop.constant.PaymentStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    @JsonProperty("order_id")
    private long id;

    @JsonProperty("order_time")
    private Date orderTime;

    @JsonProperty("ship_fee")
    private double shipFee;

    @JsonProperty("status")
    private OrderStatusEnum orderStatus;

    @JsonProperty("payment_status")
    private PaymentStatusEnum paymentStatus;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private String address;

    private UserResponse user;
    private List<ProductResponse> products;

   @Getter
   @Setter
   @AllArgsConstructor
   @NoArgsConstructor
    public static class UserResponse {
       @JsonProperty("user_id")
        private int id;

        @JsonProperty("user_name")
        private String name;
        private String email;


    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {
        private String name;
        private Double price;
        private Integer quantity;
        private String imgUrl;

    }



}
