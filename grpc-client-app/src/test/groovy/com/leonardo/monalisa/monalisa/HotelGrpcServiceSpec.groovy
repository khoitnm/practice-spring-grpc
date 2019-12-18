package com.leonardo.monalisa.monalisa

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewInsecureGrpcServiceGrpc
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewRequestProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

import java.util.stream.Collectors

class HotelGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private HotelViewInsecureGrpcServiceGrpc.HotelViewInsecureGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup() {
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
        // 3088e7c2-5684-48b8-a040-8c1e87029eb3 : Centara
        UUID oidAccount = UUID.fromString("a5f53968-fef4-401c-a518-73a506c03db1");

        when:
        HotelViewRequestProto hotelViewRequestProto = HotelViewRequestProto.newBuilder().setOidAccount(oidAccount.toString()).build()

        List<HotelViewProto> response = stub.findHotelViewsByOidAccount(hotelViewRequestProto).getHotelViewsList()
        List<String> hotelViews = response.stream()
                .filter{ hv -> hv.getVscapeOidPropertyClient() == 1266211}
                .map { hv -> toStringHotelView(hv) }
                .collect(Collectors.toList());
        printHotelViews(hotelViews);
//        System.out.println(hotelViews)

//        System.out.println(response);

        then:

        1 == 1

    }

    private static String toStringHotelView(HotelViewProto hotelViewProto) {
//        String string = "" + hotelViewProto.getOidHotelView().toString() + "_" + hotelViewProto.getVscapeOidPropertyClient() + "_" + hotelViewProto.getAddress1() + "_" + hotelViewProto.getHotelName();
        String string = hotelViewProto.toString()
        return string;
    }

    private static void printHotelViews(List<String> hotelViews) {
        for (String hotelView : hotelViews) {
            System.out.println("\n"+hotelView);
        }
    }

}
//
//oidAccount: "a5f53968-fef4-401c-a518-73a506c03db1"
//brand: "OH"
//hotelName: "Anantara Xishuangbanna Resort (Edited ICPM)"
//hotelViewId: "AXIS"
//address1: "Menglun Town Mengla County"
//city: "Xishuangbanna"
//country: "China"
//zip: "666303"
//latitude: "21.9284720000000"
//longitude: "101.2445830000000"
//phone: "86 691 893 6666"
//textInfo: "Anantara"
//oidHotelView: "aed99c37-daea-11e9-b56d-3742074ee6d3"
//vscapeOidPropertyClient: 1266211
//vscapeOidIdsClientPropertyMap: 12810984
//
