package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.clientapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory.GrpcClientThread;
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to start the server before running this test.
 */
@RunWith(SpringRunner.class)
public class SampleItemGrpcClientTest {

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    @Before
    public void setUp() {
        GrpcConnectionProperties connection = new GrpcConnectionProperties();
        connection.setHost("localhost");
        connection.setPort(6565);
        stub =  GrpcClientStubFactory.constructStub(connection, SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub.class);
    }

//    @Ignore
    @Test
    public void test_GetItem_Success() {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread clientThread = new GrpcClientThread(stub);
            threads.add(clientThread);
        }

        threads.stream().forEach(thread -> thread.start());
    }

}
