package com.uoc.myeasyjob.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.uoc.myeasyjob.util.ErrorConstants.*;

@ControllerAdvice
public class MyEasyJobExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        MyEasyJobError myEasyJobError = new MyEasyJobError();
        myEasyJobError.setErrorCode(INTERNAL_ERROR_CODE);
        myEasyJobError.setErrorMessage(ex.getMessage());
        myEasyJobError.setErrorDescription(INTERNAL_ERROR_MESSAGE);
        myEasyJobError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity(myEasyJobError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MyEasyJobException.class)
    public final ResponseEntity<Object> handleMEJExceptions(MyEasyJobException ex, WebRequest request) {
        MyEasyJobError myEasyJobError = new MyEasyJobError();
        myEasyJobError.setErrorCode(ex.getErrorCode());
        myEasyJobError.setErrorMessage(ex.getMessage());
        myEasyJobError.setErrorDescription(ex.getErrorDescription());
        myEasyJobError.setStatus(ex.getStatus());
        return new ResponseEntity(myEasyJobError, HttpStatus.valueOf(ex.getStatus()));
    }
}
