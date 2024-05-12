package com.example.demo.errors.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value() ,"Invalid parameter included"), // 잘못된 파라미터
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value(),"Invalid token"), // 인증되지 않음
    FOOD_RECOG_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.value(), "food recognition fail"), // 음식 인식 불가
    HARRIS_BENEDICT_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),"HarrisBenedict fail"), // 음식 인식 불가


    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),"Cannot find resource"), // 존재하지 않는 자원
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),"user not exists"); // 존재하지 않는 사용자

    private final HttpStatus httpStatus;
    private final Integer httpCode;
    private final String message;
}
