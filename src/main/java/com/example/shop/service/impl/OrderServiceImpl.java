package com.example.shop.service.impl;

import com.example.shop.constant.OrderStatusEnum;
import com.example.shop.constant.PaymentStatusEnum;
import com.example.shop.dto.OrderDetailResponse;
import com.example.shop.dto.OrderRequest;
import com.example.shop.dto.ProductDto;
import com.example.shop.dto.UserOrderResponse;
import com.example.shop.entity.*;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repository.OrderProductRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import com.example.shop.security.JwtTokenProvider;
import com.example.shop.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private ModelMapper modelMapper;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;

    public OrderServiceImpl(ModelMapper modelMapper, OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order newOrder = new Order();

        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("Invalid JWT"));

        User curUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        newOrder.setAddress(orderRequest.getAddress());
        newOrder.setPaymentMethod(orderRequest.getPaymentMethod());
        newOrder.setShipFee(orderRequest.getShipFee());
        newOrder.setOrderTime(new Date());
        newOrder.setOrderStatus(orderRequest.getOrderStatus() != null ? orderRequest.getOrderStatus() :  OrderStatusEnum.PROCESSING);
        newOrder.setPaymentStatus(orderRequest.getPaymentStatus() != null ? orderRequest.getPaymentStatus() : PaymentStatusEnum.NOT_COMPLETED);
        newOrder.setUser(curUser);


//        List<OrderProduct> orderProducts = orderRequest.getProducts().stream().map(cartItem ->{
//
//                    ProductDto productDto = cartItem.getProduct();
//
//                    // Fetch product from database
//                    Product product = productRepository.findById(productDto.getId())
//                            .orElseThrow(() -> new RuntimeException("Product not found"));
//                   OrderProduct orderProduct = new OrderProduct();
//                   orderProduct.setId(new OrderProductId(newOrder.getId(), cartItem.getProduct().getId()));
//                   orderProduct.setQuantity(cartItem.getQuantity());
//                   orderProduct.setProduct(product);
//                   orderProduct.setOrder(newOrder);
//                   return orderProduct;
//
//
//                }
//                ).collect(Collectors.toList());

        // optimize

        List<Long> productIds = orderRequest.getProducts().stream().map(cartItem ->
                cartItem.getProduct().getId()
                ).collect(Collectors.toList());

        // Map products to a Map<ProductId, Product> for quick lookup
        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product ));

        List<OrderProduct> orderProducts = orderRequest.getProducts().stream().map(cartItem ->{

            Product product = productMap.get(cartItem.getProduct().getId());
            if (product == null) {
                throw new RuntimeException("Product not found with ID: " + cartItem.getProduct().getId());
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(new OrderProductId(newOrder.getId(), cartItem.getProduct().getId()));
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProduct.setProduct(product);
            orderProduct.setOrder(newOrder);
            return orderProduct;

        }).collect(Collectors.toList());



        newOrder.setOrderProducts(orderProducts);
        return orderRepository.save(newOrder);
    }

    @Override
    public OrderDetailResponse getOrderDetail(long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));


        OrderDetailResponse.UserResponse userResponse = new OrderDetailResponse.UserResponse(
                order.getUser().getId(),
                order.getUser().getName(),
                order.getUser().getEmail()
        );

        List<OrderDetailResponse.ProductResponse> productResponses = order.getOrderProducts().stream().map(orderProduct ->
                new OrderDetailResponse.ProductResponse(
                        orderProduct.getProduct().getName(),
                        orderProduct.getProduct().getPrice(),
                        orderProduct.getQuantity(),
                        orderProduct.getProduct().getImgUrl()
                        )
                ).collect(Collectors.toList());


        OrderDetailResponse response = new OrderDetailResponse(
                order.getId(),
                order.getOrderTime(),
                order.getShipFee(),
                order.getOrderStatus(),
                order.getPaymentStatus(),
                order.getPaymentMethod(),
                order.getAddress(),
                userResponse,
                productResponses
        );
        return response;
    }

    // only update orderStatus and paymentStatus
    @Override
    public Order updateOrder(OrderRequest orderRequest, long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());

        return orderRepository.save(order);
    }

    @Override
    public String cancelOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setOrderStatus(OrderStatusEnum.CANCELLED);

        orderRepository.save(order);

        return "Cancelled successfully";
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<UserOrderResponse> getUserOrders() {
        String email = JwtTokenProvider.getCurrentUserLogin()
                .orElseThrow(() -> new RuntimeException("No user was found"));

        User curUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        List<Order> orders =  curUser.getOrders();
        List<Order> orders =  orderRepository.findOrdersWithProductsByUserEmail(email);

//        List<UserOrderResponse> userOrderResponses = orders.stream().map(order -> {
//            List<UserOrderResponse.ProductResponse> productResponses = order.getOrderProducts().stream().map(orderProduct ->
//                new UserOrderResponse.ProductResponse(
//                        orderProduct.getProduct().getId(),
//                        orderProduct.getProduct().getName(),
//                        orderProduct.getProduct().getPrice(),
//                        orderProduct.getQuantity(),
//                        orderProduct.getProduct().getImgUrl()
//                )
//            ).collect(Collectors.toList());

        return orders.stream().map(order -> {
            List<UserOrderResponse.ProductResponse> productResponses = order.getOrderProducts().stream().map(orderProduct ->
                    new UserOrderResponse.ProductResponse(
                            orderProduct.getProduct().getId(),
                            orderProduct.getProduct().getName(),
                            orderProduct.getProduct().getPrice(),
                            orderProduct.getQuantity(),
                            orderProduct.getProduct().getImgUrl()
                    )
            ).collect(Collectors.toList());

            return new UserOrderResponse(
                    order.getId(),
                    order.getOrderTime(),
                    order.getShipFee(),
                    order.getOrderStatus(),
                    productResponses
            );
        }).collect(Collectors.toList());


    }


}
