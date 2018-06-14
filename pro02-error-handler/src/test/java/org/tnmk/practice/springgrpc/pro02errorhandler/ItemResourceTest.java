package org.tnmk.practice.springgrpc.pro02errorhandler;

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
import org.tnmk.practice.springgrpc.pro02errorhandler.resource.ItemResource;
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;
import org.tnmk.practice.springgrpc.protobuf.ItemResourceGrpc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemResourceTest {
    /**
     * This rule manages automatic graceful shutdown for the registered servers and channels at the
     * end of test.
     */
    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule();

    @Autowired
    private ItemResource itemResource;

    private ItemResourceGrpc.ItemResourceBlockingStub stub;

    @Before
    public void setUp(){
        grpcServerRule.getServiceRegistry().addService(itemResource);
        stub = ItemResourceGrpc.newBlockingStub(grpcServerRule.getChannel());
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
