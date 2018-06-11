package org.tnmk.skeleton.springgrpc.client.grpcclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.protobuf.ItemDTO;
import org.tnmk.practice.springgrpc.protobuf.ItemIdDTO;
import org.tnmk.practice.springgrpc.protobuf.ItemProtoResourceGrpc;

@Component
public class ItemGrpcClient {

    private final ItemGrpcConnectionProperties connectionProperties;

    private ItemProtoResourceGrpc.ItemProtoResourceBlockingStub blockingStub;
    private ItemProtoResourceGrpc.ItemProtoResourceFutureStub futureStub;

    @Autowired
    public ItemGrpcClient(ItemGrpcConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(connectionProperties.getHost(), connectionProperties.getPort())
            .usePlaintext()
            .build();

        blockingStub = ItemProtoResourceGrpc.newBlockingStub(channel);
        futureStub = ItemProtoResourceGrpc.newFutureStub(channel);
    }


    public ItemDTO getItem(ItemIdDTO itemIdDTO){
        return blockingStub.getItem(itemIdDTO);
    }
}
