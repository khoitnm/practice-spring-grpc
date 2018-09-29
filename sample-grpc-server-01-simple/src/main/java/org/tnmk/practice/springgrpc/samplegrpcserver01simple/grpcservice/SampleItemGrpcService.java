package org.tnmk.practice.springgrpc.samplegrpcserver01simple.grpcservice;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.samplegrpcserver01simple.service.SampleItemService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@GRpcService
public class SampleItemGrpcService extends SampleItemGrpcServiceGrpc.SampleItemGrpcServiceImplBase {
    private final SampleItemService sampleItemService;

    @Autowired
    public SampleItemGrpcService(SampleItemService sampleItemService) {
        this.sampleItemService = sampleItemService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        try {
            ItemProto itemProto = sampleItemService.getItem(request.getId());
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
