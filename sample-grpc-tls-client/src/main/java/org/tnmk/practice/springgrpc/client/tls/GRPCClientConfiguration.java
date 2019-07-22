package org.tnmk.practice.springgrpc.client.tls;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.tnmk.common.grpc.client.GlobalGrpcClientInterceptor;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpctlsclient.ItemGrpcConnectionProperties;

import javax.net.ssl.SSLException;
import java.io.File;

/**
 * gRPC client channel builder provider with conditional TLS support.
 * The TLS support can be enabled by providing a valid CA certificate path via the configuration file:
 *
 * <pre>
 *    mobias-mona-lisa-api-gateway-grpc {
 *        port: "6576"
 *        host: "localhost"
 *        cacert: "/home/alex/Projects/self-signed-certs/ca/cacert.pem"
 *    }
 * </pre>
 */
@Configuration
public class GRPCClientConfiguration {

    private final ItemGrpcConnectionProperties grpcConnectionProperties;
    private final GlobalGrpcClientInterceptor globalGrpcClientInterceptor;

    @Autowired
    public GRPCClientConfiguration(ItemGrpcConnectionProperties grpcConnectionProperties, GlobalGrpcClientInterceptor globalGrpcClientInterceptor) {
        this.grpcConnectionProperties = grpcConnectionProperties;
        this.globalGrpcClientInterceptor = globalGrpcClientInterceptor;
    }

    @Bean
    public ManagedChannel managedChannel() {
        NettyChannelBuilder nettyChannelBuilder = NettyChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
                .intercept(globalGrpcClientInterceptor);

        if (!StringUtils.isEmpty(grpcConnectionProperties.getCaCertFilePath())) {
            nettyChannelBuilder.overrideAuthority(grpcConnectionProperties.getHost()).sslContext(createSSLContext());
        } else {
            nettyChannelBuilder.usePlaintext();
        }

        return nettyChannelBuilder.build();
    }

    private SslContext createSSLContext() {
        SslContextBuilder sslContextBuilder = GrpcSslContexts.forClient();
        File caCertFile = new File(grpcConnectionProperties.getCaCertFilePath());
        sslContextBuilder.trustManager(caCertFile);

        try {
            return sslContextBuilder.build();
        } catch (SSLException ex) {
            throw new GRPCClientStartupException("Failed to start gRPC client.", ex);
        }
    }
}
