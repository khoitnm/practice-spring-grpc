package org.tnmk.practice.springgrpc.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

@Configuration
public class ConnectionConfig {

    @Bean
    @ConfigurationProperties("grpc-connections.download-file-service")
    public GrpcConnectionProperties grpcConnectionProperties(){
        return new GrpcConnectionProperties();
    }
}
