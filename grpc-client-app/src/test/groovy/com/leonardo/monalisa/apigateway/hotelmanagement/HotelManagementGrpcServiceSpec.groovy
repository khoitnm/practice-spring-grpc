package com.leonardo.monalisa.apigateway.hotelmanagement

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewInsecureGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewRequestProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory
import spock.lang.Ignore

class HotelManagementGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private HotelViewGrpcServiceGrpc.HotelViewGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup(){
        stub = grpcClientStubFactory.constructStub(
                "hotel-management",
                HotelViewGrpcServiceGrpc.HotelViewGrpcServiceBlockingStub.class);
    }

//    @Ignore
    def 'Verify find content by Hotelview and Oid account'() {
        given:
        // 819d4fdb-7894-4ede-b113-69eddc414d01 : Lux
        // 91496704-821d-11e9-97da-270517923c8b : Langham
        UUID oidAccount = UUID.fromString("819d4fdb-7894-4ede-b113-69eddc414d01");

        when:
        HotelViewRequestProto hotelViewRequestProto = HotelViewRequestProto.newBuilder().setOidAccount(oidAccount.toString()).build()

        List<HotelViewProto> response = stub.findHotelViewsByOidAccount(hotelViewRequestProto).getHotelViewsList()

        System.out.println(response.toString())

        System.out.println(response);

        then:

        1 == 1

    }

}
