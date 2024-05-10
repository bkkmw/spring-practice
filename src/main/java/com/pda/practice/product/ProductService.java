package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductRepository productRepository;

    public int register(Product.RegisterReq registerReq) throws Exception {
        logger.debug("received with name : {}", registerReq.getName());
        return productRepository.create(registerReq);

    }

    public Product getProductInfo(int productId) throws Exception {
        logger.debug("received product id : {}", productId);
        return productRepository.findById(productId);
    }

    public List<Product> getProducts(int categoryId, int pageNo, int size) throws Exception {
        logger.debug("received categoryId : {}", categoryId);
        if(categoryId == 0) {
            return productRepository.findAll(pageNo, size);
        } else if(categoryId < 10) {
            return productRepository.findAllByCategoryId(categoryId, pageNo, size);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
