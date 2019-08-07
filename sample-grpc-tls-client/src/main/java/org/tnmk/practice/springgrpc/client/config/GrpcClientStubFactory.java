package org.tnmk.practice.springgrpc.client.config;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.tnmk.common.grpc.client.GlobalGrpcClientInterceptor;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
public class GrpcClientStubFactory {
    private final Map<String, GrpcConnectionProperties> grpcConnectionsListProperties;

    @Autowired
    public GrpcClientStubFactory(@Qualifier("grpcConnections") Map<String, GrpcConnectionProperties> grpcConnectionsListProperties) {
        this.grpcConnectionsListProperties = grpcConnectionsListProperties;
    }

    public <T, S> T constructStub(String connectionName, Class<? extends S> stubClass) {
        try {
            GrpcConnectionProperties grpcConnectionProperties = grpcConnectionsListProperties.get(connectionName);

            ClientInterceptor interceptor = new GlobalGrpcClientInterceptor();
            NettyChannelBuilder channelBuilder;
            if (StringUtils.isEmpty(grpcConnectionProperties.getTlsCertificateFilePath())) {
                channelBuilder = GrpcClientPlainTextChannelHelper.createPainTextChannelBuilder(grpcConnectionProperties, interceptor);
            } else {
                channelBuilder = GrpcClientTlsChannelHelper.createTlsChannelBuilder(grpcConnectionProperties, interceptor);
            }
            ManagedChannel channel = channelBuilder.build();

            Class grpcServiceClass = getOuterClass(stubClass);
            Method newBlockingStubMethod = grpcServiceClass.getMethod("newBlockingStub", Channel.class);
            T stub = (T) newBlockingStubMethod.invoke(null, channel);
            return stub;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Cannot get grpcClientStub: " + e.getMessage(), e);
        }
    }

    private static Class getOuterClass(Class innerClass) throws ClassNotFoundException {
        String outerClassName = innerClass.getCanonicalName().replaceAll("." + innerClass.getSimpleName(), "");
        Class outerClass = Class.forName(outerClassName);
        return outerClass;
    }
}
