package com.leonardo.monalisa.hotelmanagement

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewInsecureGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewRequestProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

import java.util.stream.Collectors

class HotelManagementInsecureGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private HotelViewInsecureGrpcServiceGrpc.HotelViewInsecureGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup(){
        stub = grpcClientStubFactory.constructStub(
                "hotel-management",
                HotelViewInsecureGrpcServiceGrpc.HotelViewInsecureGrpcServiceBlockingStub.class);
    }

//    @Ignore
    def 'Verify find content by Hotelview and Oid account'() {
        given:
        // 819d4fdb-7894-4ede-b113-69eddc414d01 : Lux
        // 91496704-821d-11e9-97da-270517923c8b : Langham
        // a5f53968-fef4-401c-a518-73a506c03db1 : Minor
        UUID oidAccount = UUID.fromString("a5f53968-fef4-401c-a518-73a506c03db1");

        when:
        HotelViewRequestProto hotelViewRequestProto = HotelViewRequestProto.newBuilder().setOidAccount(oidAccount.toString()).build()

        List<HotelViewProto> response = stub.findHotelViewsByOidAccount(hotelViewRequestProto).getHotelViewsList()
        List<String> PIDs = response.stream().map{hv -> hv.getVscapeOidPropertyClient()}.collect(Collectors.toList());
        System.out.println(PIDs)

//        System.out.println(response);

        then:

        1 == 1

    }

}
