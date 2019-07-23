package com.leonardo.monalisa.contentmanagement.content.contentsyndicationgrpcservice

import com.leonardo.monalisa.contentmanagement.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentListProto
import com.leonardo.monalisa.contentmanagement.proto.HotelViewIdProto

//import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.HotelViewIdProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.client.config.ContentVscapeSyndicatorGrpcClient

class ContentVscapeSyndicationGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private ContentVscapeSyndicatorGrpcClient grpcClient;

    def 'Verify find content by Hotelview and Oid account'() {
        given:
        UUID oidAccount = UUID.fromString("819d4fdb-7894-4ede-b113-69eddc414d01");
        String hotelViewId = "LBM"

        when:
        HotelViewIdProto request = HotelViewIdProto.newBuilder()
                .setOidAccount(oidAccount.toString())
                .setHotelViewId(hotelViewId)
                .build()
        ContentListProto response = grpcClient.getBlockingStub().findContentByHotelViewId(request)
        System.out.println("number of content "+response.getContentCount());
        System.out.println(response);


        then:
        response.getContentList().size() > 0
    }

}
