package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

public class GrpcClientThread extends Thread {
    public static final Logger logger = LoggerFactory.getLogger(GrpcClientThread.class);
    private final SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    public GrpcClientThread(SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public void run() {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId("" + System.nanoTime()).build();
        ItemProto itemProto = stub.getItem(itemIdProto);
        logger.info("correlationId from response: "+itemProto.getDescription());
        Assert.assertNotNull(itemProto.getDescription());
    }
}
