package com.example.shop.dto;


import com.example.shop.constant.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private int quantity;
    private Product product;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Product{

        @JsonProperty("product_id")
        private Long id;
        private String name;
        private double price;
        private int quantity;
        private String description;
        private String brand;
        private String imgUrl;
        private CategoryEnum category;
    }
}
