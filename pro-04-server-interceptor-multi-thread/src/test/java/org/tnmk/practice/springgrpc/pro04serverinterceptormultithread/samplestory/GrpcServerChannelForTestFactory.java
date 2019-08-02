package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory;

import io.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.testing.GrpcServerRule;

import java.io.IOException;

public class GrpcServerChannelForTestFactory {
    public static ManagedChannel createChannel(GrpcCleanupRule grpcCleanup, BindableService grpcService, ServerInterceptor serverInterceptor, ClientInterceptor... clientInterceptors) throws IOException {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(InProcessServerBuilder.forName(serverName).directExecutor()
            .addService(ServerInterceptors.intercept(grpcService, serverInterceptor))
            .build().start());

        // Create a client channel and register for automatic graceful shutdown.
        ManagedChannel channel = grpcCleanup.register(
            InProcessChannelBuilder.forName(serverName).directExecutor().build()
        );
        ClientInterceptors.intercept(channel, clientInterceptors);
        return channel;
    }

    public static ManagedChannel createChannel(GrpcServerRule grpcServerRule, BindableService grpcService, ServerInterceptor serverInterceptor, ClientInterceptor... clientInterceptors) throws IOException {
        grpcServerRule.getServiceRegistry().addService(ServerInterceptors.intercept(grpcService, serverInterceptor));
        ManagedChannel channel = grpcServerRule.getChannel();
        ClientInterceptors.intercept(channel, clientInterceptors);
        return channel;
    }
}
