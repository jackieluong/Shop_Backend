package com.example.shop.service;

import com.example.shop.dto.OrderPaymentRequest;
import com.example.shop.dto.PaymentResponse;

public interface PaymentService {

     PaymentResponse processPayment(OrderPaymentRequest orderPaymentRequest);
}
