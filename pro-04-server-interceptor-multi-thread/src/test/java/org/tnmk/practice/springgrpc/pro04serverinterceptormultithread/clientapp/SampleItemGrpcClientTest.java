package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.clientapp;

import io.grpc.testing.GrpcServerRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory.GrpcClientThread;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * You need to start the server before running this test.
 */
@RunWith(SpringRunner.class)
public class SampleItemGrpcClientTest {
    /**
     * This rule manages automatic graceful start and shutdown for the registered servers and channels at the end of test.
     */
    @Rule
    public final GrpcServerRule grpcServerRule = new GrpcServerRule();

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    @Before
    public void setUp() {
        GrpcConnectionProperties connection = new GrpcConnectionProperties();
        connection.setHost("localhost");
        connection.setPort(6565);
        stub =  GrpcClientStubFactory.constructStub(connection, SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub.class);
    }

    @Ignore
    @Test
    public void test_GetItem_Success() {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread clientThread = new GrpcClientThread(stub);
            threads.add(clientThread);
        }

        threads.stream().forEach(thread -> thread.start());
    }

}
