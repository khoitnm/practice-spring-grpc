package org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory;

import io.grpc.StatusRuntimeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.grpc.testing.GrpcServerRule;
import org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory.grpcservice.SampleItemGrpcService;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

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
    private SampleItemGrpcService sampleItemGrpcService;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    @Before
    public void setUp() {
        // Create a server, add grpc grpcservice, start, and register for automatic graceful shutdown.
        grpcServerRule.getServiceRegistry().addService(sampleItemGrpcService);

        //Connect client stub to the server via channel (the combination of address and port)
        stub = SampleItemGrpcServiceGrpc.newBlockingStub(grpcServerRule.getChannel());
    }

    @Test
    public void test_GetItem_NotEmpty_Success() {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId("1").build();
        ItemProto itemProto = stub.getItem(itemIdProto);
        Assert.assertNotNull(itemProto.getId());
    }

    @Test
    public void test_GetItem_Empty_Success() {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId("0").build();
        ItemProto itemProto = stub.getItem(itemIdProto);
        Assert.assertNotNull(itemProto.getId());
    }

    @Test(expected = StatusRuntimeException.class)
    public void test_GetItem_Error() {
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId("xxx").build();
        ItemProto itemProto = stub.getItem(itemIdProto);
        Assert.assertNotNull(itemProto.getId());
    }
}
