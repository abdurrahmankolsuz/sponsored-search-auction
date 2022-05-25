package com.check24.task.exception;

public class NotFoundAuctionException extends RuntimeException {
    public NotFoundAuctionException(String message) {
        super(message);
    }
}
