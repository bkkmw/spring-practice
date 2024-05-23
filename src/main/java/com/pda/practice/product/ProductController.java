package com.pda.practice.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody ProductDto.RegisterReq registerReq)
    {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.debug("request received with name : {}", registerReq.getSummary());
        try {
            int id = productService.register(registerReq);
            status = HttpStatus.CREATED;
            result.put("message", "Created");
            result.put("id", id);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<Map<String, Object>>(result, status);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductInfo(@PathVariable int productId) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received path variable : {} ({})", productId, productId);
        try {

            ProductDto.Info productInfo = productService.getProductInfo(productId);

            status = HttpStatus.OK;
            result.put("product", productInfo);
        } catch (NoSuchElementException e) {
            status = HttpStatus.NOT_FOUND;

        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false, defaultValue = "0") int categoryId,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "4") int size
    ) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<String, Object>();

        log.info("Received query parameter : category {}, pageNo {}, size {}", categoryId, pageNo, size);
        try {
            List<ProductDto.Info> products = productService.getProducts(categoryId, pageNo, size);

            if (products == null || products.isEmpty()) {
                status = HttpStatus.NO_CONTENT;
            } else {
                status = HttpStatus.OK;
                result.put("products", products);
            }
        } catch (IllegalArgumentException e) {
            status = HttpStatus.BAD_REQUEST;
            result.put("message", "Wrong Category Id");
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }


        return new ResponseEntity<>(result, status);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> modifyProduct(@PathVariable int productId, @RequestBody ProductDto.ModifyReq modifyReq) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();
        log.info("Received productId : {}", productId);
        try {
            productService.modifyProduct(productId, modifyReq);
            status = HttpStatus.OK;
        } catch (NoSuchElementException e) {
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable int productId) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();
        log.info("Received productId : {}", productId);

        try {
            productService.deleteProduct(productId);
            status = HttpStatus.OK;
        } catch (NoSuchElementException e) {
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }

    @PostMapping("/delete-list")
    public ResponseEntity<Map<String, Object>> deleteProducts(@RequestBody Map<String, List<Integer>> requestBody) {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.info("Received {} items to delete", requestBody.get("productIds").size());
        try {
            int res = productService.deleteProducts(requestBody.get("productIds"));

            status = HttpStatus.OK;
            result.put("deletedCount",res);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }
}
