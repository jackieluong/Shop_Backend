package com.example.shop.dto;

import com.example.shop.constant.CategoryEnum;
import com.example.shop.entity.CartProduct;
import com.example.shop.entity.FeedBack;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailsResponse {
    private long id;
    private String name;

    private double price;

    private String description;

    private String brand;

    private int quantity;

    private CategoryEnum category;

    private String imgUrl;

    @JsonProperty("reviews")
    private List<FeedBackDto> feedBacks;
}
