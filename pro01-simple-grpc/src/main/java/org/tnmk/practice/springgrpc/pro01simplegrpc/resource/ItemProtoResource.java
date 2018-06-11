package org.tnmk.practice.springgrpc.pro01simplegrpc.resource;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.tnmk.practice.springgrpc.protobuf.ItemDTO;
import org.tnmk.practice.springgrpc.protobuf.ItemIdDTO;
import org.tnmk.practice.springgrpc.protobuf.ItemProtoResourceGrpc;

import java.util.Date;

@GRpcService
public class ItemProtoResource extends ItemProtoResourceGrpc.ItemProtoResourceImplBase {

    /**
     */
    public void getItem(ItemIdDTO request, StreamObserver<ItemDTO> responseObserver) {
        ItemDTO itemDTO = ItemDTO.newBuilder()
            .setId(request.getId())
            .setName("Some name " + new Date()).build();

        responseObserver.onNext(itemDTO);
        responseObserver.onCompleted();
    }

}
