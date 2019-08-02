package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

public class GrpcClientThread extends Thread {
    public static final Logger logger = LoggerFactory.getLogger(GrpcClientThread.class);
    public static int itemId;
    private final SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    public GrpcClientThread(SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub, int itemId) {
        this.stub = stub;
        this.itemId = itemId;
    }

    @Override
    public void run() {
        try {
            ItemIdProto requestProto = ItemIdProto.newBuilder().setId("" + itemId).build();
            logger.info("start request: " + requestProto);
            ItemProto resultProto = stub.getItem(requestProto);
            logger.info("correlationId from response: [{}] {}", resultProto.getId(), resultProto.getDescription());
            Assert.assertNotNull(resultProto.getDescription());
        } catch (Exception ex) {
            logger.error("GrpcClientThread error: " + ex.getMessage(), ex);
        }
    }
}
