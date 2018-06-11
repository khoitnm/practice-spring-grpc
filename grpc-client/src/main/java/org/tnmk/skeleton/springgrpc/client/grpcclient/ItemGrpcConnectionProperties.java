package org.tnmk.skeleton.springgrpc.client.grpcclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.tnmk.skeleton.common.grpc.GrpcConnectionProperties;

@Configuration
@ConfigurationProperties(prefix = "itemgrpc.connection")
public class ItemGrpcConnectionProperties extends GrpcConnectionProperties {
}
