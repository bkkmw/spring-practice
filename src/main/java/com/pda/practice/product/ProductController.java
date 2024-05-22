package com.pda.practice.product;

import com.pda.practice.exception.ValidatorException;
import com.pda.practice.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
            @RequestBody Product.RegisterReq registerReq)
    {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        log.debug("request received with name : {}", registerReq.getSummary());
        try {
            // validate request body
            // TODO: Extract method
            if(ValidationUtil.isEmptyString(registerReq.getName()))
                throw new ValidatorException("name", "name cannot be empty");
            if(!ValidationUtil.isAlpha(registerReq.getName()))
                throw new ValidatorException("name", "name must be in english. Was : " + registerReq.getName());
            if(!ValidationUtil.isValidNumber(registerReq.getCategoryId()))
                throw new ValidatorException("categoryId", "Invalid category number. Was : " + registerReq.getCategoryId());
            if(!ValidationUtil.isValidNumber(registerReq.getPrice()))
                throw new ValidatorException("price", "Invalid price. Was : " + registerReq.getPrice());
            if(ValidationUtil.isEmptyString(registerReq.getDesc()))
                throw new ValidatorException("desc", "desc cannot be empty");
            if(!ValidationUtil.isStringInLength(registerReq.getDesc(), 20))
                throw new ValidatorException("desc", "desc cannot be longer than 20. Length was : " + registerReq.getDesc().length());


            int id = productService.register(registerReq);
            status = HttpStatus.CREATED;
            result.put("message", "Created");
            result.put("id", id);
        } catch (ValidatorException e) {
            status = HttpStatus.BAD_REQUEST;
            result.put("errors", e.getValidationErrors());
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
            if (!ValidationUtil.isValidNumber(productId)) {
                throw new ValidatorException("productId", "Invalid productId. Was : " + productId);
            }
            Product productInfo = productService.getProductInfo(productId);

            status = HttpStatus.OK;
            result.put("product", productInfo);
        } catch (ValidatorException e) {
            status = HttpStatus.BAD_REQUEST;
            result.put("errors", e.getValidationErrors());
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
            List<Product> products = productService.getProducts(categoryId, pageNo, size);

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
    public ResponseEntity<Map<String, Object>> modifyProduct(@PathVariable int productId, @RequestBody Product.ModifyReq modifyReq) {
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
