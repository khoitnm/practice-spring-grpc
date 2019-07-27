package org.tnmk.practice.springgrpc.client.tls;

public class GRPCClientStartupException extends RuntimeException {

    public GRPCClientStartupException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
