package org.tnmk.skeleton.springgrpc.client.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProtoResourceGrpc;

@Component
public class ItemGrpcClient implements ItemProtoClient {

    private final ItemGrpcConnectionProperties connectionProperties;
    private final ProtoMapper protoMapper;

    private ItemProtoResourceGrpc.ItemProtoResourceBlockingStub blockingStub;
    private ItemProtoResourceGrpc.ItemProtoResourceFutureStub futureStub;

    @Autowired
    public ItemGrpcClient(ItemGrpcConnectionProperties connectionProperties, ProtoMapper protoMapper) {
        this.connectionProperties = connectionProperties;
        this.protoMapper = protoMapper;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(connectionProperties.getHost(), connectionProperties.getPort())
            .usePlaintext()
            .build();

        blockingStub = ItemProtoResourceGrpc.newBlockingStub(channel);
        futureStub = ItemProtoResourceGrpc.newFutureStub(channel);
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
