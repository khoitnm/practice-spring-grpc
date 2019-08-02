package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.clientapp;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.AbstractStub;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GrpcClientStubFactory {

    public static <T, S extends AbstractStub> T constructStub(GrpcConnectionProperties grpcConnectionProperties, Class<? extends S> stubClass) {
        try {
            NettyChannelBuilder channelBuilder = NettyChannelBuilder
                .forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
                .usePlaintext();
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
