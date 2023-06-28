package com.musicservice.backend.controllers;

import com.musicservice.backend.payload.responses.AlreadyExistsExceptionResponse;
import com.musicservice.backend.payload.responses.NotFoundExceptionResponse;
import com.musicservice.backend.payload.responses.UnauthorizedUserExceptionResponse;
import com.musicservice.backend.exceptions.AlreadyExistsException;
import com.musicservice.backend.exceptions.NotFoundException;
import com.musicservice.backend.exceptions.UnauthorizedUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@RestController
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<NotFoundExceptionResponse> handleNotFoundException(NotFoundException ex, WebRequest req) {

        NotFoundExceptionResponse exceptionResponse = new NotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<AlreadyExistsExceptionResponse> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest req) {

        AlreadyExistsExceptionResponse exceptionResponse = new AlreadyExistsExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public final ResponseEntity<UnauthorizedUserExceptionResponse> handleUnauthorizedUserException(UnauthorizedUserException ex, WebRequest req) {

        UnauthorizedUserExceptionResponse exceptionResponse = new UnauthorizedUserExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest req) {

        BindingResult result = ex.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}