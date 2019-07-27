package org.tnmk.practice.springgrpc.grpcclientapp.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;
import org.tnmk.common.grpc.client.GrpcConnectionsListProperties;

@Configuration
public class GrpcConnectionConfig {
    @Bean("grpcConnections")
    @ConfigurationProperties("grpcConnections")
    public Map<String, GrpcConnectionProperties> grpcConnections() {
        return new HashMap<>();
    }
}
