package org.tnmk.practice.springgrpc.pro01serversimple.client

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.tnmk.practice.springgrpc.pro01serversimple.Pro01ServerSimpleApplication
import org.tnmk.practice.springgrpc.pro01serversimple.client.GrpcConnectionProperties
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto
import org.tnmk.practice.springgrpc.protobuf.ItemProto
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc
import spock.lang.Ignore
import spock.lang.Specification

@DirtiesContext
@SpringBootTest(classes = Pro01ServerSimpleApplication.class)
class SampleItemGrpcServiceGrpcSpec extends Specification {

    @Autowired
    private final GrpcConnectionProperties connectionProperties;

    private ManagedChannel channel;
    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    def setup() {
        channel = ManagedChannelBuilder.forAddress(connectionProperties.getHost(), connectionProperties.getPort()).usePlaintext().build()
        stub = SampleItemGrpcServiceGrpc.newBlockingStub(channel)
    }

    def 'Can get item from server'() {
        given:
        ItemIdProto itemIdProto = ItemIdProto.newBuilder().setId(""+System.nanoTime()).build();


        when:
        ItemProto itemProto = stub.getItem(itemIdProto);

        then:
        itemProto.getId() != null
        itemProto.getName() != null
        itemProto.getDescription() != null
    }

}