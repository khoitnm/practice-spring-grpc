package org.tnmk.practice.springgrpc.pro02serversimpleheaderanderror.grpcservice;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.grpc.support.MetadataUtils;
import org.tnmk.practice.springgrpc.pro02serversimpleheaderanderror.service.ItemNotFoundException;
import org.tnmk.practice.springgrpc.pro02serversimpleheaderanderror.service.SampleItemService;
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
        } catch (ItemNotFoundException ex) {
            Metadata metadata = new Metadata();
            MetadataUtils.putMetadata(metadata, "error-code", "ItemNotFound");//the camel case for "key" doesn't work, it will be convert to lowercase.
            MetadataUtils.putMetadata(metadata, "error-description", "Not found item " + ex.getItemId());
            MetadataUtils.putMetadata(metadata, "error-detail", ex.getItemId());
            responseObserver.onError(Status.NOT_FOUND.withCause(ex).asException(metadata));
        } catch (IllegalArgumentException ex) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT.withCause(ex).withDescription(ex.getMessage()).asException()
            );
        } catch (Exception ex) {
            responseObserver.onError(Status.UNKNOWN
                .withCause(ex)
                .withDescription(ex.getMessage())
                .asException()
            );
        }
    }

}
