package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

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
                        .id(increment++).categoryId(registerReq.getCategoryId())
                        .price(registerReq.getPrice())
                                .build();

        logger.info("created id : {}", product.getId());

        productTable.put(product.getId(), product);

        if(productTable.get(product.getId()) == null) {
            throw new Exception();
        }
        return increment-1;
    }

    public Product findById(int productId) throws Exception {
        Product product = productTable.get(productId);
        if(product == null)
            throw new NoSuchElementException();

        // TODO: add
        return product;
    }

    public List<Product> findAll(int pageNo, int size) throws Exception {
        List<Product> results = new ArrayList<>();
        int count = 0;
        Iterator<Integer> it = productTable.keySet().iterator();

        while(it.hasNext()) {
            Product product = productTable.get(it.next());

            if(count++ >= (pageNo-1)*size)
                results.add(product);

            if(count >= pageNo*size)
                break;;
        }

        return results.isEmpty() ? null : results;
    }

    public List<Product> findAllByCategoryId(int categoryId, int pageNo, int size) throws Exception {
        List<Product> results = new ArrayList<>();
        int count = 0;
        Iterator<Integer> it = productTable.keySet().iterator();

        while(it.hasNext()) {
            Product product = productTable.get(it.next());
            if(product.getCategoryId() != categoryId)
                continue;

            if(count++ >= (pageNo-1)*size)
                results.add(product);

            if(count >= pageNo*size)
                break;;
        }

        return results.isEmpty() ? null : results;
    }

    public boolean updateOneById(int productId, Product.ModifyReq modifyReq) throws Exception {
        Product product = productTable.get(productId);

        if(product == null)
            throw new NoSuchElementException();

        product.update(modifyReq);

        return productTable.put(product.getId(), product) == null;
    }

    public boolean deleteOneById(int productId) throws Exception {
        if(productTable.remove(productId) == null)
            throw new NoSuchElementException();

        return true;
    }
}
