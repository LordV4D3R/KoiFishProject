package com.sp23.koifishproject.constant.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessResult<T> {
    private int statusCode;
    private String message;
    private T data;

    public BusinessResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
