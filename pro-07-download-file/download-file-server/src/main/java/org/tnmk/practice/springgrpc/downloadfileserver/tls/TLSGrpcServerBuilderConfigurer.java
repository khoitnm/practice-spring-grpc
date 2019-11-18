package org.tnmk.practice.springgrpc.downloadfileserver.tls;

import io.grpc.ServerBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLException;
import java.io.ByteArrayInputStream;
import java.io.File;


/**
 * gRPC server configurer with TLS support.
 * <p>
 * The TLS protocol will be enabled if grpc-server-tls.enable is set to true in the configuration file.
 */
@Component
public class TLSGrpcServerBuilderConfigurer extends GRpcServerBuilderConfigurer {

    private final GrpcServerProperties grpcServerProperties;

    public TLSGrpcServerBuilderConfigurer(GrpcServerProperties grpcServerProperties) {
        this.grpcServerProperties = grpcServerProperties;
    }

    @Override
    public void configure(ServerBuilder<?> serverBuilder) {
        if (grpcServerProperties.isEnabled()){
            ((NettyServerBuilder) serverBuilder).sslContext(getSslContext());
        }
    }

    private SslContext getSslContext() {
        SslContextBuilder sslContextBuilder = SslContextBuilder.forServer(
            new File(grpcServerProperties.getCertChainFilePath()),
            new File(grpcServerProperties.getPrivateKeyFilePath())
//            new ByteArrayInputStream(grpcServerProperties.getCertChain().getBytes()),
//            new ByteArrayInputStream(grpcServerProperties.getPrivateKey().getBytes())
        );

        // Optionally enable client authentication.
        if (!StringUtils.isEmpty(grpcServerProperties.getTrustCertCollection())) {
            sslContextBuilder.trustManager(new ByteArrayInputStream(grpcServerProperties.getTrustCertCollection().getBytes()));
            sslContextBuilder.clientAuth(ClientAuth.REQUIRE);
        }

        try {
            return GrpcSslContexts.configure(sslContextBuilder).build();
        } catch (SSLException ex) {
            throw new IllegalStateException("Failed to start gRPC server.", ex);
        }
    }
}
