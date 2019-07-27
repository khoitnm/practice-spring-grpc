package com.leonardo.monalisa.contentmanagement.content.contentsyndicationgrpcservice

import com.leonardo.monalisa.contentmanagement.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentListProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc
import com.leonardo.monalisa.contentmanagement.proto.HotelViewIdProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

import java.util.stream.Collectors

class ContentVscapeSyndicationGrpcServiceSpec extends BaseSpecification {

    @Autowired
    GrpcClientStubFactory grpcClientStubFactory;
    ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub stub = grpcClientStubFactory.constructStub(
            "contentManagement",
            ContentVscapeSyndicationGrpcServiceGrpc.ContentVscapeSyndicationGrpcServiceBlockingStub.class);

    def 'Verify find content by Hotelview and Oid account'() {
        given:
        // 819d4fdb-7894-4ede-b113-69eddc414d01 : Lux
        // 91496704-821d-11e9-97da-270517923c8b : Langham
        UUID oidAccount = UUID.fromString("819d4fdb-7894-4ede-b113-69eddc414d01");
        String hotelViewId = "LPQ"

        when:
        HotelViewIdProto request = HotelViewIdProto.newBuilder()
                .setOidAccount(oidAccount.toString())
                .setHotelViewId(hotelViewId)
                .build()
        ContentListProto response = stub.findContentByHotelViewId(request)
        System.out.println("number of content " + response.getContentCount());
        String oidContentList = "\"" + response.contentList.stream().map { contentProto -> contentProto.oidContent }
                .collect(Collectors.joining("\",\"")) + "\"";
        System.out.println(oidContentList);
        System.out.println(response);

        then:
        response.getContentList().size() > 0
    }

}
