package org.tnmk.practice.springgrpc.pro01simplegrpc.resource;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.pro01simplegrpc.service.ItemProtoService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemResourceGrpc;

@GRpcService
public class ItemResource extends ItemResourceGrpc.ItemResourceImplBase {
    private final ItemProtoService itemProtoService;

    @Autowired
    public ItemResource(ItemProtoService itemProtoService) {
        this.itemProtoService = itemProtoService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        try {
            ItemProto itemProto = itemProtoService.getItem(request.getId());
            responseObserver.onNext(itemProto);
            responseObserver.onCompleted();
        } catch (Exception ex) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                .withCause(ex)
                .withDescription(ex.getMessage())
                .asException()
            );
        }
    }

}
