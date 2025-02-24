package com.example.shop.dto;

import com.example.shop.constant.OrderStatusEnum;
import com.example.shop.constant.PaymentStatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Payment method must not be blank")
    private String paymentMethod;

    @Min(value = 0, message = "Ship fee must be greater than 0")
    private double shipFee;

    @Size(min = 1, message = "Order must have at least 1 product")
    private List<OrderCartItem> products;

    private OrderStatusEnum orderStatus;

    private PaymentStatusEnum paymentStatus;

    private LocalDateTime orderTime;

}
