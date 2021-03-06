package org.tnmk.practice.springgrpc.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GrpcConnectionConfig {
    @Bean("grpcConnections")
    @ConfigurationProperties("grpc-connections")
    public Map<String, GrpcConnectionProperties> grpcConnections() {
        return new HashMap<>();
    }
}
