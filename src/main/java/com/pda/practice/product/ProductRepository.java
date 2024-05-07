package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Integer, Product> productTable = new HashMap<>();
    private int increment = 1;

    /**
     * create
     * @param registerReq
     * @return
     * @throws Exception
     */
    public int create(Product.RegisterReq registerReq) throws Exception {
        Product product = Product.builder()
                        .name(registerReq.getName()).desc(registerReq.getDesc())
                        .id(increment).categoryId(registerReq.getCategoryId())
                        .price(registerReq.getPrice())
                                .build();

        logger.info("created id : {}", product.getId());
        productTable.put(increment, product);
        return increment++;
    }

    public Product.Info findById(int productId) throws Exception {
        Product product = productTable.get(productId);
        if(product == null)
            throw new NoSuchElementException();

        // TODO: add
        return new Product.Info();
    }
}
