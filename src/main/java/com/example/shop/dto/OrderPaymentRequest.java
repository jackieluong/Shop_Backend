package com.example.shop.dto;

import com.example.shop.enums.PaymentMethodEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class OrderPaymentRequest {



    private long id;
    List<OrderCartItem> products;
    private double shipFee;

    private PaymentMethodEnum paymentMethod;

}
