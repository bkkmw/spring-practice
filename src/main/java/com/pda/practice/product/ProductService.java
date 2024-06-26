package com.pda.practice.product;

import com.pda.practice.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductJpaRepository productRepository;

    public int register(ProductDto.RegisterReq registerReq) {
        log.debug("received with name : {}", registerReq.getName());
        // TODO DTO to Entity - toEntity
        Product product = Product.builder()
                .name(registerReq.getName())
                .description(registerReq.getDesc())
                .categoryId(registerReq.getCategoryId())
                .summary(registerReq.getSummary())
                .price(registerReq.getPrice())
                .build();

        productRepository.save(product);

        log.debug("Registered product id : {}", product.getId());
        return product.getId();
    }

    public ProductDto.Info getProductInfo(int productId)  {
        log.debug("received product id : {}", productId);
        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);

        // TODO Entity to DTO
        return ProductDto.Info.builder()
                .id(product.getId())
                .categoryId(product.getCategoryId())
                .name(product.getName())
                .desc(product.getDescription())
                .summary(product.getSummary())
                .price(product.getPrice())
                .build();
    }

    // TODO Paging
    public List<ProductDto.Info> getProducts(int categoryId, int pageNo, int size) {
        log.debug("received categoryId : {}", categoryId);
        List<Product> products;
        if(categoryId == 0) {
            products = productRepository.findAll();
        } else if(categoryId < 10) { // TODO check valid category id
            products = productRepository.findAllByCategoryId(categoryId).orElseThrow(NoSuchElementException::new);
        } else {
            throw new IllegalArgumentException();
        }

        return products.stream().map((product) -> {
            return ProductDto.Info.builder()
                    .id(product.getId())
                    .categoryId(product.getCategoryId())
                    .name(product.getName())
                    .desc(product.getDescription())
                    .summary(product.getSummary())
                    .price(product.getPrice())
                    .build();
            }).collect(Collectors.toList());
    }

    // TODO method always returns true if no exception occurs, check whether entity modified or not
    public boolean modifyProduct(int productId, ProductDto.ModifyReq modifyReq) {
        log.debug("received product id : {}", productId);

        Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.update(modifyReq);

        productRepository.save(product);
        return true;
    }

    // TODO method always returns true
    public boolean deleteProduct(int productId) {
        productRepository.deleteById(productId);

        return true;
    }

    public int deleteProducts(List<Integer> productIds) {
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
