package com.example.shop.service;

import java.util.List;

import com.example.shop.dto.ProductDto;
import com.example.shop.dto.ProductRequest;
import com.example.shop.dto.ProductDetailsResponse;
import com.example.shop.entity.Product;

public interface ProductService {

    List<Product> getAllProducts();
    Product createProduct(ProductRequest product);

    ProductDetailsResponse getProductWithId(Long id);

    Product updateProduct(ProductRequest product, Long id);

    Product deleteProduct(Long id);

    List<ProductDto> searchProduct(String keyword);

}
