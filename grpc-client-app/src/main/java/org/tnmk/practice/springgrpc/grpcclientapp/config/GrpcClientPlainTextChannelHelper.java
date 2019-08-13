package org.tnmk.practice.springgrpc.grpcclientapp.config;

import io.grpc.ClientInterceptor;
//import io.grpc.netty.GrpcSslContexts;
//import io.grpc.netty.NettyChannelBuilder;
//import io.netty.handler.ssl.SslContext;
//import io.netty.handler.ssl.SslContextBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

import javax.net.ssl.SSLException;
import java.io.File;

public class GrpcClientPlainTextChannelHelper {

    public static NettyChannelBuilder createPainTextChannelBuilder(GrpcConnectionProperties grpcConnectionProperties, ClientInterceptor interceptor) {
        NettyChannelBuilder channelBuilder = NettyChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .intercept(interceptor)
            .usePlaintext();
        return channelBuilder;
    }

}
