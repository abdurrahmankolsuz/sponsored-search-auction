package com.check24.task.exception;

public class OutOfBudgetException extends RuntimeException {
    public OutOfBudgetException(String message) {
        super(message);
    }
}
