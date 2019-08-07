package org.tnmk.practice.springgrpc.client.config;

import io.grpc.ClientInterceptor;
import io.grpc.netty.NettyChannelBuilder;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

public class GrpcClientPlainTextChannelHelper {

    public static NettyChannelBuilder createPainTextChannelBuilder(GrpcConnectionProperties grpcConnectionProperties, ClientInterceptor interceptor) {
        NettyChannelBuilder channelBuilder = NettyChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .intercept(interceptor)
            .usePlaintext();
        return channelBuilder;
    }

}
