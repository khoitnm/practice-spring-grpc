package org.tnmk.practice.springgrpc.client.samplestory.samplegrpctlsclient;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.support.MetadataUtils;
import org.tnmk.practice.springgrpc.client.config.GrpcClientStubFactory;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

@Component
public class ItemSampleGrpcTlsClient {
    public static final Logger logger = LoggerFactory.getLogger(ItemSampleGrpcTlsClient.class);
    private final ItemMapper itemMapper;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub blockingStub;
    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceFutureStub futureStub;//Just to show that we can call with futureStub, we don't use it here.

    @Autowired
    public ItemSampleGrpcTlsClient(GrpcClientStubFactory grpcClientStubFactory, ItemMapper itemMapper) {
        this.itemMapper = itemMapper;

        blockingStub = grpcClientStubFactory.constructStub("sample-item", SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub.class);
    }

    public Item getItem(String itemId) {

        ItemIdProto.Builder itemIdProtoOrBuilder = ItemIdProto.newBuilder().setId(itemId);
        ItemIdProto itemIdProto = itemIdProtoOrBuilder.build();

        // WARN: Don't use attachHeaders(blockingStub, correlationId): it will create another correlationId value, with the same key.
        // so there will be many pairs with the same key correlationId, and the headers will be bigger and bigger.

//        String correlationId = (String) MDC.get(MDCConstants.CORRELATION_ID);
//        blockingStub = MetadataUtils.attachHeaders(blockingStub, correlationId);
        ItemProto itemProto;
        try {
            itemProto = blockingStub.getItem(itemIdProto);
        } catch (StatusRuntimeException ex) {
            Metadata metadata = ex.getTrailers();
            String errorCode = MetadataUtils.getStringValue(metadata, "error-code");
            String errorDescription = MetadataUtils.getStringValue(metadata, "error-description");
            String errorDetails = MetadataUtils.getStringValue(metadata, "error-details");
            if ("ItemNotFound".equalsIgnoreCase(errorCode)) {
                return null;
            } else {
                throw new GetItemException("Cannot get item " + itemIdProto + ". errorDescription: " + errorDescription + ". errorDetails: " + errorDetails+". ExceptionMessage: "+ex.getMessage(), ex);
            }
        }
        logger.info("Get Item from Server: "+itemProto);
        return itemMapper.toItem(itemProto);
    }
}
