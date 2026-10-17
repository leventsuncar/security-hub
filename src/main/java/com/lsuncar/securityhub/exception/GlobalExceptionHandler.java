package com.lsuncar.securityhub.exception;

import com.lsuncar.securityhub.model.response.results.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResult> handleUserAlreadyExist(
            UserAlreadyExistException e) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResult(e.getCode()));
    }

}
