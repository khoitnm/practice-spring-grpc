package org.tnmk.practice.springgrpc.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

@Configuration
public class GrpcConnectionConfig {
    @Bean
    @ConfigurationProperties("itemgrpc")
    public GrpcConnectionProperties grpcConnectionProperties() {
        return new GrpcConnectionProperties();
    }
}
