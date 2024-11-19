package com.project.backend.exception;

public class InvalidPasswordException extends AuthException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}