package com.example.shop.dto;

import com.example.shop.enums.OrderStatusEnum;
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
public class UserOrderResponse {
    @JsonProperty("order_id")
    private long id;

    @JsonProperty("order_time")
    private Date orderTime;

    @JsonProperty("ship_fee")
    private double shipFee;

    @JsonProperty("status")
    private OrderStatusEnum orderStatus;

    private List<ProductResponse> products;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductResponse {

        @JsonProperty("product_id")
        private long id;
        private String name;
        private Double price;
        private Integer quantity;
        private String imgUrl;

    }
}
