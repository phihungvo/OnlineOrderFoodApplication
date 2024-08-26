package com.luv2code.Online.Food.Ordering.exception;

import com.luv2code.Online.Food.Ordering.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> runtimeExceptionHandling(RuntimeException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        exceptionResponse.setCode(errorCode.getCode());
        exceptionResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ExceptionResponse> appExceptionHandling(AppException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ErrorCode errorCode = exception.getErrorCode();

        exceptionResponse.setCode(errorCode.getCode());
        exceptionResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<ExceptionResponse> userExceptionHandling(UserException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        ErrorCode errorCode = exception.getErrorCode();

        exceptionResponse.setCode(errorCode.getCode());
        exceptionResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedExceptionHandling(AccessDeniedException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse();

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        exceptionResponse.setCode(errorCode.getCode());
        exceptionResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }



}
