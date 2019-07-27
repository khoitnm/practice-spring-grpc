package org.tnmk.practice.springgrpc.grpcclientapp.config;

import io.grpc.Channel;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.client.GlobalGrpcClientInterceptor;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;
import org.tnmk.common.grpc.client.GrpcConnectionsListProperties;

@Component
public class GrpcClientStubFactory {
    private final Map<String, GrpcConnectionProperties> grpcConnectionsListProperties;

    @Autowired
    public GrpcClientStubFactory(@Qualifier("grpcConnections") Map<String, GrpcConnectionProperties> grpcConnectionsListProperties) {
        this.grpcConnectionsListProperties = grpcConnectionsListProperties;
    }

    public <T, S> T constructStub(String connectionName, Class<? extends S> stubClass) {
        try {
            GrpcConnectionProperties grpcConnectionProperties= grpcConnectionsListProperties.get(connectionName);

            ClientInterceptor interceptor = new GlobalGrpcClientInterceptor();
            ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
                    .intercept(interceptor)
                    .usePlaintext()
                    .build();
            String grpcServiceClassName = stubClass.getCanonicalName().replaceAll("BlockingStub", "Grpc");
            Class grpcServiceClass = Class.forName(grpcServiceClassName);
            Method newBlockingStubMethod = grpcServiceClass.getMethod("newBlockingStub", Channel.class);
            T stub = (T) newBlockingStubMethod.invoke(null, channel);
            return stub;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Cannot get grpcClientStub: " + e.getMessage(), e);
        }
    }
}
