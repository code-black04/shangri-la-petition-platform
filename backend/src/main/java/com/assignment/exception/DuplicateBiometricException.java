package com.assignment.exception;

public class DuplicateBiometricException extends RuntimeException{

    public DuplicateBiometricException(String message) {
        super(message);
    }

    public DuplicateBiometricException(String message, Throwable cause) {
        super(message, cause);
    }
}
