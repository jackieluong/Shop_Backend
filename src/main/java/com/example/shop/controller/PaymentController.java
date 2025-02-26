package com.example.shop.controller;

import com.example.shop.dto.OrderPaymentRequest;
import com.example.shop.dto.PaymentResponse;
import com.example.shop.enums.PaymentMethodEnum;
import com.example.shop.service.PaymentService;
import com.example.shop.service.impl.StripePaymentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/checkout")
public class PaymentController {
    private final Map<PaymentMethodEnum, PaymentService> paymentServices;

    public PaymentController(List<PaymentService> paymentServiceList) {
        this.paymentServices = paymentServiceList.stream()
                .collect(Collectors.toMap(this::getPaymentMethod, paymentService -> paymentService));

        System.out.println("PaymentController initialized with services: " + paymentServices.keySet());
    }

    private PaymentMethodEnum getPaymentMethod(PaymentService paymentService) {
        if (paymentService instanceof StripePaymentServiceImpl) {
            return PaymentMethodEnum.CREDIT_CARD;
        }
        // Add more mappings for other payment methods
        return null;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody OrderPaymentRequest orderPaymentRequest) {

//        if(orderPaymentRequest.getPaymentMethod().equals(PaymentMethodEnum.CREDIT_CARD)){
//            paymentService = new StripePaymentServiceImpl();
//        }
        PaymentService paymentService = paymentServices.get(orderPaymentRequest.getPaymentMethod());

        PaymentResponse paymentResponse = paymentService.processPayment(orderPaymentRequest);


//        ResponseDto responseDto = new ResponseDto.Builder()
//                .setStatus(HttpStatus.OK)
//                .setMessage("Create order successfully")
//                .setData(paymentResponse)
//                .build();

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }



}
