package org.tnmk.practice.springgrpc.client.config;

import io.grpc.ClientInterceptor;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

import javax.net.ssl.SSLException;
import java.io.File;

public class GrpcClientTlsChannelHelper {

    public static NettyChannelBuilder createTlsChannelBuilder(GrpcConnectionProperties grpcConnectionProperties, ClientInterceptor interceptor) {
        SslContext sslContext = createSSLContext(grpcConnectionProperties.getTlsCertificateFilePath());
        NettyChannelBuilder nettyChannelBuilder = NettyChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .overrideAuthority(grpcConnectionProperties.getHost())
            .sslContext(sslContext)
            .intercept(interceptor);
        return nettyChannelBuilder;
    }

    private static SslContext createSSLContext(String tlsCertificateAbsoluteFilePath) {
        SslContextBuilder sslContextBuilder = GrpcSslContexts.forClient();
        sslContextBuilder.trustManager(new File(tlsCertificateAbsoluteFilePath));

        try {
            return sslContextBuilder.build();
        } catch (SSLException ex) {
            throw new IllegalStateException("Cannot build sslContext for gRPC: " + ex.getMessage(), ex);
        }
    }
}
