package com.check24.task.exception;

public class NotFoundSlotException extends RuntimeException {
    public NotFoundSlotException(String message) {
        super(message);
    }
}
