package org.tnmk.practice.springgrpc.pro02serversimpleheaderanderror.grpcservice;

import io.grpc.Status;
import io.grpc.StatusException;

public class ItemNotFoundGrpcException extends StatusException {
    private final String errorCode;
    private final String errorDescription;
    private final Object errorDetails;
    public ItemNotFoundGrpcException(Status status, String errorCode, String errorDescription, Object errorDetails) {
        super(status);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorDetails = errorDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public Object getErrorDetails() {
        return errorDetails;
    }
}
