package com.example.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartRequest {
    @JsonProperty("product_id")
    @NotNull(message = "Product ID must not be blank")
    private long productID;

    private int quantity;
}
