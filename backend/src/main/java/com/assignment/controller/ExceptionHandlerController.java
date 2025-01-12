package com.assignment.controller;

import com.assignment.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<Object> handleAccountAlreadyExists(DuplicateAccountException exceptions){
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_ACCOUNT_EXISTS",
                List.of(new ErrorMessage("userAccountDetails", exceptions.getMessage())),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(BadRequestException exceptions){
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_BAD_REQUEST",
                List.of(new ErrorMessage("request", exceptions.getMessage())),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Object> handleBadRequest(UnauthorizedAccessException exceptions){
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_UNAUTHORIZED_ACCESS",
                List.of(new ErrorMessage("request", exceptions.getMessage())),
                LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_USER_NOT_FOUND",
                List.of(new ErrorMessage("username_not_found", ex.getMessage())),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Object> handleUserNotFound(IncorrectPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_AUTH_FAILED",
                List.of(new ErrorMessage("password_incorrect", ex.getMessage())),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PetitionNotFoundException.class)
    public ResponseEntity<Object> handlePetitionNotFound(PetitionNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_PETITION_NOT_FOUND",
                List.of(new ErrorMessage("petition_not_found", ex.getMessage())),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ErrorMessage> errorMessages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "ERR_VALIDATION_FAILED",
                errorMessages,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorResponse {

        private final String errorCode;
        private final List<ErrorMessage> errorMessageList;
        private final LocalDateTime timeStamp;

        public ErrorResponse(String errorCode, List<ErrorMessage> errorMessageList, LocalDateTime timeStamp) {
            this.errorCode = errorCode;
            this.timeStamp = timeStamp;
            this.errorMessageList = errorMessageList;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public List<ErrorMessage> getErrorMessageList() {
            return errorMessageList;
        }

        public LocalDateTime getTimeStamp() {
            return timeStamp;
        }

        @Override
        public String toString() {
            return "ErrorResponse{" +
                    "errorCode='" + errorCode + '\'' +
                    ", errorMessageList=" + errorMessageList +
                    ", timeStamp=" + timeStamp +
                    '}';
        }
    }

    public static class ErrorMessage {

        private String fieldName;

        private String error;

        public ErrorMessage(String fieldName, String error) {
            this.fieldName = fieldName;
            this.error = error;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getError() {
            return error;
        }

        @Override
        public String toString() {
            return "ErrorMessage{" +
                    "fieldName='" + fieldName + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }
}
