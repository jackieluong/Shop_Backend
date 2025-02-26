package com.example.shop.dto;

import com.example.shop.enums.CategoryEnum;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequest {
    
    @NotBlank(message = "Product name must not be blank")
    private String name;

    @Min(value= 1, message = "Product price must be greater than 0")
    private long price;

    private String description;

    @NotBlank(message = "Product brand must not be blank")
    private String brand;


    @Min(value = 1, message = "Product quantity must be greater than 0")
    private int quantity;

    private CategoryEnum category;

    private String imgUrl;

}
