package org.tnmk.practice.springgrpc.grpcclientapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

@Configuration
public class GrpcConnectionConfig {
    @Bean("contentManagementConnectionProperties")
    @ConfigurationProperties("content-management")
    public GrpcConnectionProperties contentManagementConnectionProperties() {
        return new GrpcConnectionProperties();
    }
}
