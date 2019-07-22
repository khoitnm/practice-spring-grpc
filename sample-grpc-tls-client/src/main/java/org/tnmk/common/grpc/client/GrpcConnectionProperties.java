package org.tnmk.common.grpc.client;

public class GrpcConnectionProperties {
    private String host;
    private int port;
    private String caCertFilePath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCaCertFilePath() {
        return caCertFilePath;
    }

    public void setCaCertFilePath(String caCertFilePath) {
        this.caCertFilePath = caCertFilePath;
    }
}
