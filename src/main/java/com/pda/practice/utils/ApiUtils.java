package com.pda.practice.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils<T> {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<T>(response);
    }

    public static <T> ApiResult<T> error(T message, HttpStatus status) {
        return new ApiResult<T>(new ApiError<T>(message, status));
    }

    @Getter
    public static class ApiResult<T> {
        boolean success;
        T response;
        ApiError<T> error;

        public ApiResult(T response) {
            this.success = true;
            this.response = response;
        }

        public ApiResult(ApiError<T> error) {
            this.success = false;
            this.error = error;
        }
    }

    @Getter
    public static class ApiError<T> {
        T message;
        HttpStatus httpStatus;

        public ApiError(T message, HttpStatus httpStatus) {
            this.message = message;
            this.httpStatus = httpStatus;
        }
    }
}
