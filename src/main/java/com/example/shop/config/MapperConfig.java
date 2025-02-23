package com.example.shop.config;

import com.example.shop.dto.ProductDetailsResponse;
import com.example.shop.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setCollectionsMergeEnabled(false)  // Disable collections merging
//                .setSkipNullEnabled(true);
//        return modelMapper;
//    }

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper.createTypeMap(Product.class, ProductDetailsResponse.class)
//                .addMappings(mapper -> {
//                    mapper.map(src -> new ArrayList<>(src.getFeedBacks()),
//                            ProductDetailsResponse::setFeedBacks);
//                });
//
//        return modelMapper;
//    }

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper.getConfiguration()
//                .setPropertyCondition(context -> context.getSource() != null)
//                .setSkipNullEnabled(true)
//                .setCollectionsMergeEnabled(false);
//
//        // Configure explicit mapping for Product to ProductDetailsResponse
//        modelMapper.typeMap(Product.class, ProductDetailsResponse.class)
//                .setPostConverter(context -> {
//                    Product source = context.getSource();
//                    ProductDetailsResponse destination = context.getDestination();
//
//                    if (source.getFeedBacks() != null) {
//                        destination.setFeedBacks(new ArrayList<>(source.getFeedBacks()));
//                    } else {
//                        destination.setFeedBacks(new ArrayList<>());
//                    }
//
//                    return destination;
//                });
//
//        return modelMapper;
//    }



}