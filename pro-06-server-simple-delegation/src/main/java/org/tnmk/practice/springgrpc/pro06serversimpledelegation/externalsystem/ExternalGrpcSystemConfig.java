package org.tnmk.practice.springgrpc.pro06serversimpledelegation.externalsystem;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.common.client.GrpcConnectionProperties;

@Configuration
public class ExternalGrpcSystemConfig {

    @Bean
    @ConfigurationProperties(prefix = "external-grpc-server")
    public GrpcConnectionProperties externalGrpcServerConnection(){
        return new GrpcConnectionProperties();
    }
}
