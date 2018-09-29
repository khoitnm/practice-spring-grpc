package org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory.resource;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.grpc.global.GlobalGrpcInterceptor;
import org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory.service.ItemProtoService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemResourceGrpc;

import java.util.Optional;

@GRpcService
public class ItemResource extends ItemResourceGrpc.ItemResourceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalGrpcInterceptor.class);

    private final ItemProtoService itemProtoService;

    @Autowired
    public ItemResource(ItemProtoService itemProtoService) {
        this.itemProtoService = itemProtoService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        LOGGER.info("RESOURCE: getItem: " + request);
        Optional<ItemProto> itemProto = itemProtoService.getItem(request.getId());
        responseObserver.onNext(itemProto.orElse(null));
        responseObserver.onCompleted();
    }

}
