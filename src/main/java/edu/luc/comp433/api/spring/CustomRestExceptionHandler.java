package edu.luc.comp433.api.spring;

import edu.luc.comp433.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({EntryNotFoundException.class})
    public ResponseEntity<Object> handleEntryNotFound(
            EntryNotFoundException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({DuplicatedEntryException.class})
    public ResponseEntity<Object> handleDuplicatedEntry(
            DuplicatedEntryException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({QuantityNotSufficientException.class})
    public ResponseEntity<Object> handleNotSufficientQuantity(
            QuantityNotSufficientException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({InvalidStatusException.class})
    public ResponseEntity<Object> handleInvalidStatus(
            InvalidStatusException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }



    @ExceptionHandler({NotRemovableException.class})
    public ResponseEntity<Object> handleNotRemovable(
            NotRemovableException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleServerSideError(
            Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        ApiError apiError =
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
