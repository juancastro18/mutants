package com.meli.mutants.controllers.advicer;

import com.meli.mutants.businessExceptions.InvalidAdnLetterException;
import com.meli.mutants.businessExceptions.InvalidSizeException;
import com.meli.mutants.businessExceptions.NoMutantException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomApiExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoMutantException.class)
    protected ResponseEntity<Object> noMutantException(
            NoMutantException noMutantException) {
        return new ResponseEntity<>(noMutantException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidAdnLetterException.class)
    protected ResponseEntity<Object> invalidAdnLetterException(
            InvalidAdnLetterException invalidAdnLetterException) {
        return new ResponseEntity<>(invalidAdnLetterException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidSizeException.class)
    protected ResponseEntity<Object> invalidSizeException(
            InvalidSizeException invalidSizeException) {
        return new ResponseEntity<>(invalidSizeException.getMessage(), HttpStatus.FORBIDDEN);
    }

}
