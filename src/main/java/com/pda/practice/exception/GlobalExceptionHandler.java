package com.pda.practice.exception;

import com.pda.practice.utils.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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

    @ExceptionHandler(value = {DuplicatedKeyException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiUtils.ApiResult<Object> handleDuplicatedKey(Exception e) {
        String message = e.getMessage();
        log.info("Duplicated DB column");

        return ApiUtils.error(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiUtils.ApiResult<Object> handleNoSuchElementException(NoSuchElementException e) {
        String message = e.getMessage();

        log.info("Element Not Found");

        return ApiUtils.error(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiUtils.ApiResult<Object> handleUnintendedException(Exception e) {
        String message = e.getMessage();

        log.warn("Unintended Error : {}", (Object) e.getStackTrace());

        return ApiUtils.error(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
