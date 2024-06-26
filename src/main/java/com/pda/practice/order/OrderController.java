package com.pda.practice.order;

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
            return ApiUtils.success(orderService.register(registerReq));
        } catch (NoSuchElementException e) {
            return ApiUtils.error("No such product", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiUtils.error("Unintended Server error"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
