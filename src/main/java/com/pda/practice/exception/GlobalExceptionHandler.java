package com.pda.practice.exception;

import com.pda.practice.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiUtils.ApiResult<Object> handleBadRequest(MethodArgumentNotValidException e) {
        Map<String, Object> message = new HashMap<>();

        log.warn("Exception !");

        message.put("message", e.getFieldErrors());
        return ApiUtils.error(message, HttpStatus.BAD_REQUEST);
    }
}
