package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public int register(Product.RegisterReq registerReq) throws Exception {
        logger.debug("received with name : {}", registerReq.getName());
        return productRepository.create(registerReq);

    }

    public Product.Info getProductInfo(int productId) throws Exception {
        logger.debug("received product id : {}", productId);
        return productRepository.findById(productId);
    }
}
