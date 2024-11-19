package com.project.backend.exception;

public class DuplicateUserException extends AuthException {
    public DuplicateUserException(String message) {
        super(message);
    }
}