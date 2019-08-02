package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory.grpcservice;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.grpc.global.GlobalGrpcServerInterceptor;
import org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory.service.SampleItemService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

import static org.tnmk.common.grpc.global.MDCConstants.CORRELATION_ID;
//We don't need to declare the GlobalGrpcServerInterceptor here.
//@GRpcService(interceptors = GlobalGrpcServerInterceptor.class)
@GRpcService
public class SampleItemGrpcService extends SampleItemGrpcServiceGrpc.SampleItemGrpcServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(SampleItemGrpcService.class);

    private final SampleItemService sampleItemService;

    @Autowired
    public SampleItemGrpcService(SampleItemService sampleItemService) {
        this.sampleItemService = sampleItemService;
    }

    public void getItem(ItemIdProto request, StreamObserver<ItemProto> responseObserver) {
        String correlationId = MDC.get(CORRELATION_ID);
        logger.info("GrpcService. getCorrelationId: {}", correlationId);
        ItemProto itemProto = sampleItemService.getItem(correlationId, request.getId());
        responseObserver.onNext(itemProto);
        responseObserver.onCompleted();
    }

}
