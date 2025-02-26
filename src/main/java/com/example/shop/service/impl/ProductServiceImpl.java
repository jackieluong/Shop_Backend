package com.example.shop.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.shop.dto.FeedBackDto;
import com.example.shop.dto.ProductDto;
import com.example.shop.entity.FeedBack;
import com.example.shop.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.dto.ProductRequest;
import com.example.shop.dto.ProductDetailsResponse;
import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }
    @Override
    public Product createProduct(ProductRequest product) {
        // TODO Auto-generated method stub

        Product newProduct = productRepository.save(mapper.map(product, Product.class));
        return newProduct;
    }

    @Override
    public Product deleteProduct(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductDto> searchProduct(String keyword) {

        List<Product> products = productRepository.searchByKeyword(keyword);

        return products.stream().map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    @Transactional
    public ProductDetailsResponse getProductWithId(Long id) {

        Product product = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));
//        ProductDetailsResponse productDetailsResponse = mapper.map(product, ProductDetailsResponse.class);


        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setId(product.getId());
        productDetailsResponse.setName(product.getName());
        productDetailsResponse.setBrand(product.getBrand());
        productDetailsResponse.setCategory(product.getCategory());
        productDetailsResponse.setDescription(product.getDescription());
        productDetailsResponse.setImgUrl(product.getImgUrl());
        productDetailsResponse.setPrice(product.getPrice());
        productDetailsResponse.setQuantity(product.getQuantity());

        List<FeedBackDto> feedBackDtos = product.getFeedBacks().stream().map(feedBack ->
                new FeedBackDto(feedBack.getId(), feedBack.getContent(), feedBack.getScore(),feedBack.getFeedbackTime(),
                        feedBack.getUser() != null ? feedBack.getUser().getName() : "Anonymous"
                        )).collect(Collectors.toList());

//        productDetailsResponse.setFeedBacks(product.getFeedBacks());
        productDetailsResponse.setFeedBacks(feedBackDtos);




        return productDetailsResponse;
    }



    @Override
    public Product updateProduct(ProductRequest product, Long id) {

        Product updatedProduct = productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setQuantity(product.getQuantity());
        updatedProduct.setImgUrl(product.getImgUrl());
        updatedProduct.setBrand(product.getBrand());

        return productRepository.save(updatedProduct);

        
    }
    
}
