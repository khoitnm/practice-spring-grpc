package org.tnmk.practice.springgrpc.pro06serversimpledelegation.spocktest.client

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.Pro01Application
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.common.client.GrpcConnectionProperties
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto
import org.tnmk.practice.springgrpc.protobuf.ItemProto
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc
import spock.lang.Specification
@ActiveProfiles("componenttest")
@SpringBootTest(classes = Pro01Application.class)
class ContentGrpcServiceGrpcSpec extends Specification {

    @Qualifier("sampleItemGrpcServerConnection")
    @Autowired
    private GrpcConnectionProperties connectionProperties;

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
    }

}