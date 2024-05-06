package com.example.demo.errors;

import com.example.demo.errors.errorCode.CommonErrorCode;
import com.example.demo.errors.errorCode.ErrorCode;
import com.example.demo.errors.exception.RestApiException;
import com.example.demo.errors.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleCustomException(final RestApiException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(final Exception ex) {
        log.warn("handleAllException", ex);
        final ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }



    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return com.example.demo.errors.response.ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }


    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    // 스프링 내부 로직 custom exception 적용
    // https://github.com/MangKyu/InterviewSubscription/blob/master/src/main/java/com/mangkyu/employment/interview/erros/handler/GlobalExceptionHandler.java
    // https://mangkyu.tistory.com/205

    // 스프링 필터에서 적용되는 exception -> 401/403
    // https://ailiartsua.tistory.com/35

}
