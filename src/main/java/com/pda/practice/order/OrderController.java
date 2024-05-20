package com.pda.practice.order;

import com.pda.practice.exception.ValidatorException;
import com.pda.practice.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    public ApiUtils.ApiResult<Object> register(@RequestBody Order.RegisterReq registerReq) {

        try {
            if (registerReq.getProductId() == 0 || registerReq.getUserId() == null) {
                throw new ValidatorException("foreign key", "foreign key must not be empty");
            }
            return ApiUtils.success(orderService.register(registerReq));
        } catch (ValidatorException e) {
            return ApiUtils.error("Invalid input", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return ApiUtils.error("No such product", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiUtils.error("Unintended Server error"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
