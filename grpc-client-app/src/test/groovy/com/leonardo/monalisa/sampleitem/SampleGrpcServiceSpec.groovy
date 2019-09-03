package com.leonardo.monalisa.sampleitem

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewRequestProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory
import org.tnmk.practice.springgrpc.protobuf.ItemIdProto
import org.tnmk.practice.springgrpc.protobuf.ItemProto
import org.tnmk.practice.springgrpc.protobuf.SampleItemGrpcServiceGrpc
import spock.lang.Ignore

class SampleGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup(){
        stub = grpcClientStubFactory.constructStub(
                "sample-item",
                SampleItemGrpcServiceGrpc.SampleItemGrpcServiceBlockingStub.class);
    }

    @Ignore
    def 'Get Sampe Item via TLS gRPC'() {
        given:

        when:
        ItemIdProto idProto = ItemIdProto.newBuilder().setId(""+System.nanoTime()).build();

        ItemProto itemProto = stub.getItem(idProto)

        System.out.println(itemProto)

        then:
        noExceptionThrown()
    }

}
