package org.tnmk.practice.springgrpc.client.samplestory.samplegrpcclient;

import io.grpc.*;
import io.grpc.stub.AbstractStub;
import org.jboss.logging.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.client.GlobalGrpcClientInterceptor;
import org.tnmk.common.grpc.support.MetadataUtils;
import org.tnmk.practice.springgrpc.client.common.MDCConstants;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@Component
public class ItemSampleGrpcClient {

    private final ItemGrpcConnectionProperties connectionProperties;
    private final ItemMapper itemMapper;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub blockingStub;
    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceFutureStub futureStub;//Just to show that we can call with futureStub, we don't use it here.

    @Autowired
    public ItemSampleGrpcClient(ItemGrpcConnectionProperties connectionProperties, ItemMapper itemMapper) {
        this.connectionProperties = connectionProperties;
        this.itemMapper = itemMapper;

        ClientInterceptor interceptor = new GlobalGrpcClientInterceptor();
        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.connectionProperties.getHost(), this.connectionProperties.getPort())
            .intercept(interceptor)
            .usePlaintext()
            .build();

        blockingStub = SampleItemGrpcServiceGrpc.newBlockingStub(channel);
//        futureStub = SampleItemGrpcServiceGrpc.newFutureStub(channel);
    }

    public Item getItem(ItemId itemId) {
        ItemIdProto.Builder itemIdProtoOrBuilder = ItemIdProto.newBuilder();
        BeanUtils.copyProperties(itemId, itemIdProtoOrBuilder);
        ItemIdProto itemIdProto = itemIdProtoOrBuilder.build();

        
        // WARN: Don't use attachHeaders(blockingStub, correlationId): it will create another correlationId value, with the same key.
        // so there will be many pairs with the same key correlationId, and the headers will be bigger and bigger.

//        String correlationId = (String) MDC.get(MDCConstants.CORRELATION_ID);
//        blockingStub = MetadataUtils.attachHeaders(blockingStub, correlationId);
        ItemProto itemProto = blockingStub.getItem(itemIdProto);
        return itemMapper.toItem(itemProto);
    }
}
