package org.tnmk.common.grpc.client;

public class GrpcConnectionProperties {
    private String host;
    private int port;
    /**
     * In the future, we may want to support both absolute file path and class path.
     * However, for now, we only support absolute file path.
     * Note: this is the path to TLS's ca certificate (cacert.pem)
     */
    private String tlsCertificateFilePath;

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


    public String getTlsCertificateFilePath() {
        return tlsCertificateFilePath;
    }

    public void setTlsCertificateFilePath(String tlsCertificateFilePath) {
        this.tlsCertificateFilePath = tlsCertificateFilePath;
    }
}
