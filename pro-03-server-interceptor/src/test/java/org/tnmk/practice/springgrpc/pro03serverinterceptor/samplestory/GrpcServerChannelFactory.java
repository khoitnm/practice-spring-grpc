package org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory;

import io.grpc.*;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.testing.GrpcServerRule;

import java.io.IOException;

public class GrpcServerChannelFactory {
    public static ManagedChannel createChannel(GrpcCleanupRule grpcCleanup, BindableService grpcService, ServerInterceptor serverInterceptor, ClientInterceptor... clientInterceptors) throws IOException {
        // Generate a unique in-process server name.
        String serverName = InProcessServerBuilder.generateName();

        //Create a server, add grpc grpcservice, start, and register for automatic graceful shutdown.
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

    /**
     * Create a server, add grpc grpcservice, start, and register for automatic graceful shutdown.
     *
     * @param grpcServerRule
     * @param grpcService
     * @param serverInterceptor
     * @param clientInterceptors
     * @return
     * @throws IOException
     */
    public static ManagedChannel createChannel(GrpcServerRule grpcServerRule, BindableService grpcService, ServerInterceptor serverInterceptor, ClientInterceptor... clientInterceptors) throws IOException {
        grpcServerRule.getServiceRegistry().addService(ServerInterceptors.intercept(grpcService, serverInterceptor));
        ManagedChannel channel = grpcServerRule.getChannel();
        ClientInterceptors.intercept(channel, clientInterceptors);
        return channel;
    }
}
