package org.tnmk.practice.springgrpc.samplegrpctlsserver.tls;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties("grpc-server-tls")
public class GrpcServerProperties {

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
}
