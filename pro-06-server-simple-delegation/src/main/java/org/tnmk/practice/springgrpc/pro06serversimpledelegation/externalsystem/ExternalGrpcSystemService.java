package org.tnmk.practice.springgrpc.pro06serversimpledelegation.externalsystem;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.common.client.GrpcConnectionProperties;
import org.tnmk.practice.springgrpc.protobuf.ExternalGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

/**
 * This class is supposed to be used to delegate requests to another grpc-server.
 * In our case, we delegate request to itself.
 * Note: be careful don't create endless loop.
 */
@Service
public class ExternalGrpcSystemService {

    private final GrpcConnectionProperties grpcConnectionProperties;
    private final ExternalGrpcServiceGrpc.ExternalGrpcServiceBlockingStub blockingStub;

    @Autowired
    public ExternalGrpcSystemService(@Qualifier("externalGrpcServerConnection") GrpcConnectionProperties grpcConnectionProperties) {
        this.grpcConnectionProperties = grpcConnectionProperties;
        ManagedChannel channel = ManagedChannelBuilder.forAddress(this.grpcConnectionProperties.getHost(), this.grpcConnectionProperties.getPort())
            .usePlaintext()
            .build();

        blockingStub = ExternalGrpcServiceGrpc.newBlockingStub(channel);
    }

    public ItemProto getExternalItem(String id) {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder()
            .setId(id)
            .build();
        ItemProto itemProto = blockingStub.getExternalItem(itemIdProto);
        return itemProto;
    }
}
