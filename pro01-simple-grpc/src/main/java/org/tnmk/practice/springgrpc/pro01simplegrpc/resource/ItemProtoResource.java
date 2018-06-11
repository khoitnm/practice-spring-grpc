package org.tnmk.practice.springgrpc.pro01simplegrpc.resource;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProtoResourceGrpc;

import java.util.Date;

@GRpcService
public class ItemProtoResource extends ItemProtoResourceGrpc.ItemProtoResourceImplBase {

    /**
     */
    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        ItemProto itemProto = ItemProto.newBuilder()
            .setId(request.getId())
            .setName("Some name " + new Date()).build();

        responseObserver.onNext(itemProto);
        responseObserver.onCompleted();
    }

}
