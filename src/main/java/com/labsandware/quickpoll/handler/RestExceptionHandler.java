package com.labsandware.quickpoll.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;


import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.labsandware.quickpoll.error.ErrorDetail;
import com.labsandware.quickpoll.dto.error.ValidationError;
import com.labsandware.quickpoll.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler  {

    @Inject
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException (
            ResourceNotFoundException rnfe, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<?> handleMethodArgumentNotValid (
            MethodArgumentNotValidException mavne, HttpServletRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestPath == null) {
            requestPath = request.getRequestURI();
        }
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(mavne.getMessage());
        errorDetail.setDeveloperMessage(mavne.getClass().getName());

        List<FieldError> fieldErrors  = mavne.getBindingResult().getFieldErrors();
        for (FieldError fe:
                fieldErrors) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().computeIfAbsent(fe.getField(), k -> new ArrayList<ValidationError>());
            ValidationError validationError =  new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);
        }

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

//    protected ResponseEntity<Object>
//    handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
//                                 HttpStatus status, WebRequest request) {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimeStamp(new Date().getTime());
//        errorDetail.setStatus(status.value());
//        errorDetail.setTitle("Message Not Readable");
//        errorDetail.setDetail(ex.getMessage());
//        errorDetail.setDeveloperMessage(ex.getClass().getName());
//
//        return handleExceptionInternal(ex, errorDetail, headers, status, request);
//    }
}
