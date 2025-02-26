package com.example.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {

        private String status;
        private String message;
        private String sessionId;
        private String sessionUrl;


}
