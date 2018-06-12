package org.tnmk.practice.springgrpc.pro02errorhandler.resource;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.pro02errorhandler.errorhandler.GlobalInterceptor;
import org.tnmk.practice.springgrpc.pro02errorhandler.service.ItemProtoService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProtoResourceGrpc;

import java.util.Optional;

@GRpcService
public class ItemProtoResource extends ItemProtoResourceGrpc.ItemProtoResourceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalInterceptor.class);

    private final ItemProtoService itemProtoService;

    @Autowired
    public ItemProtoResource(ItemProtoService itemProtoService) {
        this.itemProtoService = itemProtoService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        LOGGER.info("RESOURCE: getItem: " + request);
        Optional<ItemProto> itemProto = itemProtoService.getItem(request.getId());
        responseObserver.onNext(itemProto.orElse(null));
        responseObserver.onCompleted();
    }

}
