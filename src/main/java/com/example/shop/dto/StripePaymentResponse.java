package com.example.shop.dto;

import lombok.*;

@Getter
@Setter

//@Builder
public class StripePaymentResponse extends PaymentResponse{


    StripePaymentResponse(String status, String message, String sessionId, String sessionUrl) {
        super(status, message, sessionId, sessionUrl);
    }
}
