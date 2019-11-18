package org.tnmk.practice.springgrpc.downloadfileserver.tls;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("grpc-server-tls")
public class GrpcServerProperties {
    private boolean enabled;

    /**
     * The path to server/cert.pem file
     */
    private String certChainFilePath;

    /**
     * The path to server/private_key.pkcs8.pem file
     */
    private String privateKeyFilePath;

    private String certChain;

    private String privateKey;

    private String trustCertCollection;

    public String getCertChain() {
        return certChain;
    }

    public void setCertChain(String certChain) {
        this.certChain = certChain;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTrustCertCollection() {
        return trustCertCollection;
    }

    public void setTrustCertCollection(String trustCertCollection) {
        this.trustCertCollection = trustCertCollection;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCertChainFilePath() {
        return certChainFilePath;
    }

    public void setCertChainFilePath(String certChainFilePath) {
        this.certChainFilePath = certChainFilePath;
    }

    public String getPrivateKeyFilePath() {
        return privateKeyFilePath;
    }

    public void setPrivateKeyFilePath(String privateKeyFilePath) {
        this.privateKeyFilePath = privateKeyFilePath;
    }
}
