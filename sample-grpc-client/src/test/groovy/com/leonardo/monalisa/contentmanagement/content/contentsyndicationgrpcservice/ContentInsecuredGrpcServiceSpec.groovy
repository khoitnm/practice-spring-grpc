package com.leonardo.monalisa.contentmanagement.content.contentsyndicationgrpcservice

import com.leonardo.monalisa.contentmanagement.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentListProto
import com.leonardo.monalisa.contentmanagement.content.proto.OidContentListProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.client.config.ContentInsecureGrpcClient

class ContentInsecuredGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private ContentInsecureGrpcClient grpcClient;

    def 'Verify find content by Hotelview and Oid account'() {
        given:
        List<String> oidContentList = new ArrayList<>();

        when:
        OidContentListProto proto = OidContentListProto.newBuilder()
                .addAllOidContent(oidContentList)
                .build()

        ContentListProto response = grpcClient.getBlockingStub().insecureFindContentListByOidContentList(request)
        System.out.println("number of content "+response.getContentCount());
        System.out.println(response);


        then:
        response.getContentList().size() == 1
    }

}
