package com.example.demo.errors.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    Integer getHttpCode();
    String getMessage();
}
