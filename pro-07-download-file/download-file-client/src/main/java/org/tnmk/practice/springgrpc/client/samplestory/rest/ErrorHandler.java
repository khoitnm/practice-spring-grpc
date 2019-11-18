package org.tnmk.practice.springgrpc.client.samplestory.rest;

import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(StatusRuntimeException.class)
    public ErrorResult handleGrpcException(StatusRuntimeException ex) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setErrorCode("GRPC_ERROR");
        errorResult.setMessage("Message: " + ex.getMessage()
            + ", Status: " + ex.getStatus()
            + ", Trailers: " + ex.getTrailers());
        return errorResult;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception ex) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.setErrorCode("UNKNOWN_ERROR");
        errorResult.setMessage(ex.getMessage());
        return errorResult;
    }

}
