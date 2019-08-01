package org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory.grpcservice;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory.service.SampleItemService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@GRpcService
public class SampleItemGrpcService extends SampleItemGrpcServiceGrpc.SampleItemGrpcServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleItemGrpcService.class);

    private final SampleItemService sampleItemService;

    @Autowired
    public SampleItemGrpcService(SampleItemService sampleItemService) {
        this.sampleItemService = sampleItemService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        LOGGER.info("RESOURCE: getItem: " + request);
        ItemProto itemProto = sampleItemService.getItem(request.getId());
        responseObserver.onNext(itemProto);
        responseObserver.onCompleted();
    }

}
