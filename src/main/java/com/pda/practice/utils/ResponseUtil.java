package com.pda.practice.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static <T> ResponseTemplate<T> success(T payload) {
        return new ResponseTemplate<>(true, payload, null);
    }

    public static ErrorResponseTemplate error(Throwable throwable, String message) {
        return new ErrorResponseTemplate(false, throwable, message);
    }

    public static class ResponseTemplate<T> {
        private final boolean success;
        private final T payload;
        private final String message;

        public ResponseTemplate(boolean success, T payload, String message) {
            this.success = success;
            this.payload = payload;
            this.message = message;
        }


        public Map<String, Object> toMap() {
            return new HashMap<String, Object>() {{
                put("success", success);
                put("response", payload);
                put("message", message);
            }};
        }
    }

    public static class ErrorResponseTemplate {
        private final boolean success;
        private final String message;
        private final Throwable throwable;

        public ErrorResponseTemplate(boolean success, Throwable throwable, String message) {
            this.success = success;
            this.throwable = throwable;
            this.message = message;
        }

        public Map<String, Object> toMap() {
            return new HashMap<String, Object>() {{
                put("success", success);
                put("message", message);
                put("error", throwable.getMessage());
            }};
        }
    }
}
