package com.david.javamoim.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String message;
    private T body;

    private ApiResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(message, null);
    }

    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<T>("", body);
    }
}
