//package org.tnmk.practice.springgrpc.client.config;
//
//import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc;
//import io.grpc.ClientInterceptor;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.tnmk.common.grpc.client.GlobalGrpcClientInterceptor;
//import org.tnmk.common.grpc.client.GrpcConnectionProperties;
//
//@Component
//public class ContentVscapeSyndicatorGrpcClient {
//
//    private final GrpcConnectionProperties connectionProperties;
//
//    private final ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub blockingStub;
//
//    @Autowired
//    public ContentVscapeSyndicatorGrpcClient(@Qualifier("contentManagementConnectionProperties") GrpcConnectionProperties connectionProperties) {
//        this.connectionProperties = connectionProperties;
//
//        ClientInterceptor interceptor = new GlobalGrpcClientInterceptor();
//        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.connectionProperties.getHost(), this.connectionProperties.getPort())
//            .intercept(interceptor)
//            .usePlaintext()
//            .build();
//
//        blockingStub = ContentVscapeSyndicationGrpcServiceGrpc.newBlockingStub(channel);
//    }
//
//    public ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub getBlockingStub() {
//        return blockingStub;
//    }
//}
