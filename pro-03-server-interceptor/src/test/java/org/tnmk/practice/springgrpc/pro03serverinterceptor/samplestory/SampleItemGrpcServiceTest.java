package org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory;

import io.grpc.ManagedChannel;
import io.grpc.testing.GrpcServerRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.common.grpc.global.GlobalGrpcServerInterceptor;
import org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory.grpcservice.SampleItemGrpcService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

import java.io.IOException;

/**
 * More additional reference links:
 * <li>
 * This link use new grpc version with many use cases:
 * https://github.com/grpc/grpc-java/blob/master/testing/src/test/java/io/grpc/testing/GrpcServerRuleTest.java
 * </li>
 *
 * <li>
 * This link use old grpc version but has some clear explanations:
 * https://github.com/grpc/grpc-java/blob/master/examples/src/test/java/io/grpc/examples/helloworld/HelloWorldServerTest.java
 * </li>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleItemGrpcServiceTest {
    /**
     * This rule manages automatic graceful start and shutdown for the registered servers and channels at the end of test.
     */
    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule();

    @Autowired
    private GlobalGrpcServerInterceptor globalGrpcServerInterceptor;

    @Autowired
    private SampleItemGrpcService sampleItemGrpcService;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    @Before
    public void setUp() throws IOException {
        ManagedChannel channel = GrpcServerChannelFactory.createChannel(grpcServerRule, sampleItemGrpcService, globalGrpcServerInterceptor);
        //Connect client stub to the server via channel (the combination of address and port)
        stub = SampleItemGrpcServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void test_GetItem_Success() {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId("" + System.nanoTime()).build();
        ItemProto itemProto = stub.getItem(itemIdProto);
        Assert.assertNotNull(itemProto.getId());
    }

}
