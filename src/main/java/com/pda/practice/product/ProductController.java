package com.pda.practice.product;

import com.pda.practice.exception.ValidatorException;
import com.pda.practice.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody Product.RegisterReq registerReq)
    {
        HttpStatus status;
        Map<String, Object> result = new HashMap<>();

        logger.debug("request received with name : {}", registerReq.getSummary());
        try {
            // validate request body
            // TODO: Extract method
            if(ValidationUtil.isEmptyString(registerReq.getName()))
                throw new ValidatorException("name", "name cannot be empty");
            if(!ValidationUtil.isAlpha(registerReq.getName()))
                throw new ValidatorException("name", "name must be in english. Was : " + registerReq.getName());
            if(ValidationUtil.isValidNumber(registerReq.getCategoryId()))
                throw new ValidatorException("categoryId", "Invalid category number. Was : " + registerReq.getCategoryId());
            if(ValidationUtil.isValidNumber(registerReq.getPrice()))
                throw new ValidatorException("price", "Invalid price. Was : " + registerReq.getPrice());
            if(ValidationUtil.isEmptyString(registerReq.getDesc()))
                throw new ValidatorException("desc", "desc cannot be empty");
            if(!ValidationUtil.isStringInLength(registerReq.getDesc(), 20))
                throw new ValidatorException("desc", "desc cannot be longer than 20. Length was : " + registerReq.getDesc().length());


            long id = productService.register(registerReq);
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

        try {
            Product.Info productInfo = productService.getProductInfo(productId);

            status = HttpStatus.OK;
            result.put("product", productInfo);
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("message", e.getMessage());
        }

        return new ResponseEntity<>(result, status);
    }
}
