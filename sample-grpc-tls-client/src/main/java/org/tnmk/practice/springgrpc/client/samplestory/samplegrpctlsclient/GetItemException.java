package org.tnmk.practice.springgrpc.client.samplestory.samplegrpctlsclient;

public class GetItemException extends RuntimeException {
    public GetItemException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
