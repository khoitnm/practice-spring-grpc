package org.tnmk.practice.springgrpc.samplegrpcserver01simple.service;

public class ItemNotFoundException extends RuntimeException {
    private final String itemId;

    public ItemNotFoundException(String message, String itemId) {
        super(message);
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
