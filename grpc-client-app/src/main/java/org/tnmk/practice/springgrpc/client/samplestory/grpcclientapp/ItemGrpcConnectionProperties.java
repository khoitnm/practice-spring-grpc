package org.tnmk.practice.springgrpc.client.samplestory.grpcclientapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

@Configuration
@ConfigurationProperties(prefix = "itemgrpc.connection")
public class ItemGrpcConnectionProperties extends GrpcConnectionProperties {
}
