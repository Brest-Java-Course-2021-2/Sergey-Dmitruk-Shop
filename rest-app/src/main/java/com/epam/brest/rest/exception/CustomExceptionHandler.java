package com.epam.brest.rest.exception;

import com.epam.brest.service.impl.exception.DepartmentNotFoundException;
import com.epam.brest.service.impl.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String PRODUCT_NOT_FOUND = "product.not_found";
    public static final String DEPARTMENT_NOT_FOUND = "department.not_found";
    public static final String VALIDATION_ERROR = "validation_error";

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handlerIllegalArgumentException(Exception ex, WebRequest request) {

        return new ResponseEntity<>(
                new ErrorResponse(VALIDATION_ERROR, ex),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlerDepartmentNotFoundException(DepartmentNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(DEPARTMENT_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DateTimeException.class})
    public ResponseEntity<ErrorResponse> handlerDateFormatException(DateTimeException ex) {
        return new ResponseEntity<>(new ErrorResponse("Date format not validated", ex), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlerProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(PRODUCT_NOT_FOUND, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
