package org.tnmk.practice.springgrpc.pro06serversimpledelegation.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.common.client.GrpcConnectionProperties;

@Configuration
public class SampleItemGrpcServiceConfig {

    @Bean
    @ConfigurationProperties(prefix = "sample-item-grpc-server")
    public GrpcConnectionProperties sampleItemGrpcServerConnection(){
        return new GrpcConnectionProperties();
    }
}
