package org.tnmk.practice.springgrpc.samplegrpcserver01simple.resource;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.samplegrpcserver01simple.service.ItemProtoService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@GRpcService
public class SampleItemGrpcService extends SampleItemGrpcServiceGrpc.SampleItemGrpcServiceImplBase {
    private final ItemProtoService itemProtoService;

    @Autowired
    public SampleItemGrpcService(ItemProtoService itemProtoService) {
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
