package com.pda.practice.order;

import com.pda.practice.product.ProductJpaRepository;
import com.pda.practice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductJpaRepository productRepository;
    private final UserRepository userRepository;

    public Order register(Order.RegisterReq registerReq) throws Exception {

//        Order order = new Order();
//        order.setProduct(productRepository.findById(registerReq.getProductId()));
//        order.setUser(userRepository.findById(registerReq.getUserId()));
//        order.setQuantity(registerReq.getQuantity());
        Order order = new Order(
                0,
                productRepository.findById(registerReq.getProductId()).get(),
                userRepository.findByUserId(registerReq.getUserId()),
                registerReq.getQuantity()
                );
        return orderRepository.create(order);

    }
}
