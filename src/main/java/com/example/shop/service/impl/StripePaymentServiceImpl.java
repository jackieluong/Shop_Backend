package com.example.shop.service.impl;

import com.example.shop.dto.OrderPaymentRequest;
import com.example.shop.dto.PaymentResponse;
import com.example.shop.dto.StripePaymentResponse;
import com.example.shop.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItem;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StripePaymentServiceImpl implements PaymentService {

    private final String successUrl = "http://localhost:8080";
    private final String cancelUrl = "http://localhost:8080";
    private String currency = "vnd";
    @Value("${stripe.secretKey}")
    private String stripeSecretKey;




    @PostConstruct
    public void init() {
        System.out.println("Stripe Secret Key: " + stripeSecretKey); // for debugging
    }

    @Override
    public PaymentResponse processPayment(OrderPaymentRequest orderPaymentRequest) {
        Stripe.apiKey = stripeSecretKey;
//        Stripe.apiKey = "sk_test_51QwDlZFYxQml8f30gX52JnMsVs5pJM3VmuOi2kvlPkdtpahREvbx7c2NaAuUEbGN3caBvEFeEciiPtuwQaZ3Kdsd00hNxCipZs";

        List<SessionCreateParams.LineItem> lineItems = orderPaymentRequest.getProducts().stream().map(cartProduct -> {

                    SessionCreateParams.LineItem.PriceData.ProductData productData= SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(cartProduct.getProduct().getName())
                            .setDescription(cartProduct.getProduct().getDescription())
                            .build();

                    SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(currency)
                            .setProductData(productData)
                            .setUnitAmount((long) (cartProduct.getProduct().getPrice()))
                            .build();

                    SessionCreateParams.LineItem lineItem = new SessionCreateParams.LineItem.Builder()
                    .setPriceData(priceData)
                    .setQuantity((long) cartProduct.getQuantity())
                    .build();

            return lineItem;

        }
        ).collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl)
                .setCurrency("vnd")
                .build();



        try{
            Session session = Session.create(params);
            return StripePaymentResponse.builder()
                    .status("SUCCESS")
                    .message("Successful payment ")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build() ;
        }catch (StripeException e){
            System.out.println("Stripe exception: " + e.getMessage());
            return StripePaymentResponse.builder()
                    .status("FAILED")
                    .message("Stripe error: " + e.getMessage())
                    .build() ;
        }




    }
}
