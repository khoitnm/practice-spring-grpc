package org.tnmk.common.grpc.support;

public class ReflectionGetPropertyException extends RuntimeException {
    public ReflectionGetPropertyException(String message, Throwable ex) {
        super(message, ex);
    }
}
