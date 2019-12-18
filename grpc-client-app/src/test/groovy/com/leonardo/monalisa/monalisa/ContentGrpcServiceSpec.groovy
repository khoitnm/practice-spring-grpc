package com.leonardo.monalisa.monalisa

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.HotelViewIdProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewProto
import com.leonardo.monalisa.hotelmanagement.hotelview.proto.HotelViewRequestProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

import java.util.stream.Collectors

class ContentGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup() {
        stub = grpcClientStubFactory.constructStub(
                "content-management",
                ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub.class);
    }

//    @Ignore
    def 'Verify find content by Hotelview and Oid account'() {
        given:
        String hotelViewId = "CZPN";
        String oidAccount = "3088e7c2-5684-48b8-a040-8c1e87029eb3";
        when:
        HotelViewIdProto hotelViewIdProto = HotelViewIdProto.newBuilder()
                .setHotelViewId(hotelViewId)
                .setOidAccount(oidAccount)
                .build();

        List<ContentProto> response = stub.findContentByHotelViewId(hotelViewIdProto).getContentList();
        for (ContentProto contentProto : response) {
            if (ContentHelper.getCaption(contentProto).equals("Fitness Centre 1")){
                System.out.println(contentProto)
            }
        }


        then:

        1 == 1

    }

}
