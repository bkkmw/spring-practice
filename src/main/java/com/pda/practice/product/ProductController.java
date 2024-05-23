package com.pda.practice.product;

import com.pda.practice.utils.ApiUtils;
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
    public ApiUtils.ApiResult<Object> register(
            @RequestBody ProductDto.RegisterReq registerReq)
    {
        Map<String, Object> result = new HashMap<>();

        log.debug("request received with name : {}", registerReq.getSummary());
        int id = productService.register(registerReq);
        result.put("message", "Created");
        result.put("id", id);

        return ApiUtils.success(result);
    }

    @GetMapping("/{productId}")
    public ApiUtils.ApiResult<Object> getProductInfo(@PathVariable int productId) {
        Map<String, Object> result = new HashMap<>();

        log.info("Received path variable : {} ({})", productId, productId);
        ProductDto.Info productInfo = productService.getProductInfo(productId);

        result.put("product", productInfo);

        return ApiUtils.success(result);
    }

    @GetMapping("")
    public ApiUtils.ApiResult<Object> getProducts(
            @RequestParam(required = false, defaultValue = "0") int categoryId,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "4") int size
    ) {
        Map<String, Object> result = new HashMap<String, Object>();

        log.info("Received query parameter : category {}, pageNo {}, size {}", categoryId, pageNo, size);
        try {
            List<ProductDto.Info> products = productService.getProducts(categoryId, pageNo, size);

            // TODO: empty result
            result.put("products", products);

            return ApiUtils.success(result);
        } catch (IllegalArgumentException e) {
            log.info("Wrong Category Id : {}", categoryId);
            return ApiUtils.error("Wrong Category Id", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}")
    public ApiUtils.ApiResult<Object> modifyProduct(@PathVariable int productId, @RequestBody ProductDto.ModifyReq modifyReq) {
        Map<String, Object> result = new HashMap<>();
        log.info("Received productId : {}", productId);

        productService.modifyProduct(productId, modifyReq);

        return ApiUtils.success(result);
    }

    @DeleteMapping("/{productId}")
    public ApiUtils.ApiResult<Object> deleteProduct(@PathVariable int productId) {
        Map<String, Object> result = new HashMap<>();
        log.info("Received productId : {}", productId);

        productService.deleteProduct(productId);

        return ApiUtils.success(result);
    }

    @PostMapping("/delete-list")
    public ApiUtils.ApiResult<Object> deleteProducts(@RequestBody Map<String, List<Integer>> requestBody) {
        Map<String, Object> result = new HashMap<>();

        log.info("Received {} items to delete", requestBody.get("productIds").size());
        int res = productService.deleteProducts(requestBody.get("productIds"));
        result.put("deletedCount",res);

        return ApiUtils.success(result);
    }
}
