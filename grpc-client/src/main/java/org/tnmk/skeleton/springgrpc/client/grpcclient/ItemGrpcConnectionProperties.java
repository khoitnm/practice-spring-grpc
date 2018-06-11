package org.tnmk.skeleton.springgrpc.client.grpcclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.tnmk.skeleton.springgrpc.client.common.GrpcConnectionProperties;

@Configuration
@ConfigurationProperties(prefix = "itemgrpc.connection")
public class ItemGrpcConnectionProperties extends GrpcConnectionProperties {
}
