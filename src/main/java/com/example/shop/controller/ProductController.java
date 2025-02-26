package com.example.shop.controller;

import java.util.List;

import com.example.shop.dto.ProductDetailsResponse;
import com.example.shop.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.shop.dto.ProductRequest;
import com.example.shop.dto.ResponseDto;
import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    
    private ProductService productService;
    
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllProducts(@RequestParam(value = "current", defaultValue = "1") int current,
                                               @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {

        List<Product> products = productService.getAllProducts();
        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Get all products successfully")
                .setData(products)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product newProduct = productService.createProduct(productRequest);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.CREATED)
                .setMessage("Create product successfully")
                .setData(newProduct)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDto> updateProduct(@Valid @RequestBody ProductRequest productRequest, @PathVariable(name = "id") Long id) {

        Product updatedProduct = productService.updateProduct(productRequest, id);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Update product successfully")
                .setData(updatedProduct)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getProductById( @PathVariable(name = "id") Long id) {

        ProductDetailsResponse product = productService.getProductWithId(id);

        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Get product successfully")
                .setData(product)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto> searchProduct(@RequestParam(value = "current", defaultValue = "1") int current,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                                     @RequestParam String keyword) {
        keyword = keyword.trim();
        List<ProductDto> productDtos = productService.searchProduct(keyword);
        ResponseDto responseDto = new ResponseDto.Builder()
                .setStatus(HttpStatus.OK)
                .setMessage("Search product successfully")
                .setData(productDtos)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}

