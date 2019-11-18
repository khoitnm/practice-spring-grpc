package org.tnmk.practice.springgrpc.downloadfileserver.service;

public class IllegalItemIdException extends RuntimeException {
    private final String itemId;

    public IllegalItemIdException(String message, String itemId) {
        super(message);
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
