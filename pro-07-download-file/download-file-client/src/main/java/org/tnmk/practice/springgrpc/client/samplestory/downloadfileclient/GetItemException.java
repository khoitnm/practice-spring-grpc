package org.tnmk.practice.springgrpc.client.samplestory.downloadfileclient;

public class GetItemException extends RuntimeException {
    public GetItemException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
