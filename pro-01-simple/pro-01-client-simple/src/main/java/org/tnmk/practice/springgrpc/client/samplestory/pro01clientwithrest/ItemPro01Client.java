package org.tnmk.practice.springgrpc.client.samplestory.pro01clientwithrest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.client.config.GrpcConnectionProperties;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@Component
public class ItemPro01Client {

    private final GrpcConnectionProperties connectionProperties;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub blockingStub;
    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceFutureStub futureStub;//Just to show that we can call with futureStub, we don't use it here.

    @Autowired
    public ItemPro01Client(GrpcConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.connectionProperties.getHost(), this.connectionProperties.getPort())
                .usePlaintext()
                .build();

        blockingStub = SampleItemGrpcServiceGrpc.newBlockingStub(channel);
    }

    public Item getItem(String itemId) {
        ItemIdProto requestProto = ItemIdProto.newBuilder().setId(itemId).build();
        ItemProto responseProto = blockingStub.getItem(requestProto);
        return ItemMapper.toItem(responseProto);
    }
}
