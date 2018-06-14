package org.tnmk.skeleton.springgrpc.client.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemResourceGrpc;

@Component
public class ItemGrpcClient implements ItemProtoClient {

    private final ItemGrpcConnectionProperties connectionProperties;
    private final ProtoMapper protoMapper;

    private ItemResourceGrpc.ItemResourceBlockingStub blockingStub;
    private ItemResourceGrpc.ItemResourceFutureStub futureStub;//Just to show that we can call with futureStub, we don't use it here.

    @Autowired
    public ItemGrpcClient(ItemGrpcConnectionProperties connectionProperties, ProtoMapper protoMapper) {
        this.connectionProperties = connectionProperties;
        this.protoMapper = protoMapper;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.connectionProperties.getHost(), this.connectionProperties.getPort())
            .usePlaintext()
            .build();

        blockingStub = ItemResourceGrpc.newBlockingStub(channel);
        futureStub = ItemResourceGrpc.newFutureStub(channel);
    }


    @Override
    public Item getItem(ItemId itemId){
        ItemIdProto.Builder itemIdProtoOrBuilder = ItemIdProto.newBuilder();
        BeanUtils.copyProperties(itemId, itemIdProtoOrBuilder);
        ItemIdProto itemIdProto = itemIdProtoOrBuilder.build();
        ItemProto itemProto = blockingStub.getItem(itemIdProto);
        return protoMapper.itemProtoToItem(itemProto);
    }
}
