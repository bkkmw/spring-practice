package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public int register(Product.RegisterReq registerReq) throws Exception {
        log.debug("received with name : {}", registerReq.getName());
        return productRepository.create(registerReq);

    }

    public Product getProductInfo(int productId) throws Exception {
        log.debug("received product id : {}", productId);
        return productRepository.findById(productId);
    }

    public List<Product> getProducts(int categoryId, int pageNo, int size) throws Exception {
        log.debug("received categoryId : {}", categoryId);
        if(categoryId == 0) {
            return productRepository.findAll(pageNo, size);
        } else if(categoryId < 10) {
            return productRepository.findAllByCategoryId(categoryId, pageNo, size);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean modifyProduct(int productId, Product.ModifyReq modifyReq) throws Exception {
        log.debug("received product id : {}", productId);

        return productRepository.updateOneById(productId, modifyReq);
    }

    public boolean deleteProduct(int productId) throws Exception {
        return productRepository.deleteOneById(productId);
    }

    public int deleteProducts(List<Integer> productIds) throws Exception {
        int count = 0;
        for(Integer productId : productIds) {
            try {
                count += deleteProduct(productId)? 1 : 0;
            } catch (NoSuchElementException e) {
                log.info("Failed to delete product. Product {} not found", productId);
            }
        }

        return count;
    }
}
