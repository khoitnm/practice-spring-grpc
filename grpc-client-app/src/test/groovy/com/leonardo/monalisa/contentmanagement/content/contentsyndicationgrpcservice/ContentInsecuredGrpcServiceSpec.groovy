package com.leonardo.monalisa.contentmanagement.content.contentsyndicationgrpcservice

import com.leonardo.monalisa.contentmanagement.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentInsecureGrpcServiceGrpc
import com.leonardo.monalisa.contentmanagement.content.proto.ContentListProto
import com.leonardo.monalisa.contentmanagement.content.proto.OidContentListProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

class ContentInsecuredGrpcServiceSpec extends BaseSpecification {

    @Autowired
    GrpcClientStubFactory grpcClientStubFactory;

    private ContentInsecureGrpcServiceGrpc.ContentInsecureGrpcServiceBlockingStub stub;

    /**
     * run before the first feature method
     * @return
     */
    def setup(){
        stub = grpcClientStubFactory.constructStub(
                "content-management",
                ContentInsecureGrpcServiceGrpc.ContentInsecureGrpcServiceBlockingStub.class);
    }

    def 'Verify find content by Hotelview and Oid account'() {
        given:
        List<String> oidContentList = Arrays.asList("030991fa-acce-11e9-ae20-0d3f11b256be", "0920b69b-acce-11e9-ae20-0d3f11b256be", "09d97c72-acca-11e9-ae20-0d3f11b256be", "09f5b9dc-acce-11e9-ae20-0d3f11b256be", "0a4ad3fc-accd-11e9-ae20-0d3f11b256be", "0aaaa73d-accd-11e9-ae20-0d3f11b256be", "0b01049e-accd-11e9-ae20-0d3f11b256be", "0b0c32ed-acce-11e9-ae20-0d3f11b256be", "0b90735f-accd-11e9-ae20-0d3f11b256be", "0bd78e80-accd-11e9-ae20-0d3f11b256be", "0c42ac61-accd-11e9-ae20-0d3f11b256be", "0c93e45e-acce-11e9-ae20-0d3f11b256be", "0c9a7133-acca-11e9-ae20-0d3f11b256be", "0d97e6df-acce-11e9-ae20-0d3f11b256be", "0de48824-accb-11e9-ae20-0d3f11b256be", "0e2c1875-accb-11e9-ae20-0d3f11b256be", "0ebf76f0-acce-11e9-ae20-0d3f11b256be", "0f2559f2-accd-11e9-ae20-0d3f11b256be", "10711e91-acce-11e9-ae20-0d3f11b256be", "112cca94-acca-11e9-ae20-0d3f11b256be", "1141d153-accd-11e9-ae20-0d3f11b256be", "12191e84-accd-11e9-ae20-0d3f11b256be", "12d5adff-accc-11e9-ae20-0d3f11b256be", "148851a5-acca-11e9-ae20-0d3f11b256be", "154a8855-accd-11e9-ae20-0d3f11b256be", "15a82ef0-accc-11e9-ae20-0d3f11b256be", "17efd501-accc-11e9-ae20-0d3f11b256be", "18c69796-acca-11e9-ae20-0d3f11b256be", "1a19eab6-accd-11e9-ae20-0d3f11b256be", "1a1dea37-acca-11e9-ae20-0d3f11b256be", "1b37bea8-acca-11e9-ae20-0d3f11b256be", "1c230909-acca-11e9-ae20-0d3f11b256be", "1e9846e7-accd-11e9-ae20-0d3f11b256be", "1ecf163a-acca-11e9-ae20-0d3f11b256be", "21501bcb-acca-11e9-ae20-0d3f11b256be", "256ef2dc-acca-11e9-ae20-0d3f11b256be", "25da7e08-accd-11e9-ae20-0d3f11b256be", "275b51a9-accd-11e9-ae20-0d3f11b256be", "28f55a7d-acca-11e9-ae20-0d3f11b256be", "2b549f5a-accd-11e9-ae20-0d3f11b256be", "2c91bb1e-acca-11e9-ae20-0d3f11b256be", "2cee040b-accd-11e9-ae20-0d3f11b256be", "2ea817ff-acca-11e9-ae20-0d3f11b256be", "304a7d60-acca-11e9-ae20-0d3f11b256be", "322501dc-accd-11e9-ae20-0d3f11b256be", "3233afc1-acca-11e9-ae20-0d3f11b256be", "32c7d18d-accd-11e9-ae20-0d3f11b256be", "33569a42-accc-11e9-ae20-0d3f11b256be", "338b7792-acca-11e9-ae20-0d3f11b256be", "34b63bf3-acca-11e9-ae20-0d3f11b256be", "36308a96-accb-11e9-ae20-0d3f11b256be", "374c8c93-accc-11e9-ae20-0d3f11b256be", "384686be-accd-11e9-ae20-0d3f11b256be", "3c948c04-acca-11e9-ae20-0d3f11b256be", "4169169f-accd-11e9-ae20-0d3f11b256be", "43f34bd5-acca-11e9-ae20-0d3f11b256be", "483840a0-accd-11e9-ae20-0d3f11b256be", "48a70801-accd-11e9-ae20-0d3f11b256be", "4c84bf46-acca-11e9-ae20-0d3f11b256be", "500d2d67-accb-11e9-ae20-0d3f11b256be", "50906728-accb-11e9-ae20-0d3f11b256be", "50d426e9-accb-11e9-ae20-0d3f11b256be", "515516ba-accb-11e9-ae20-0d3f11b256be", "519b206b-accb-11e9-ae20-0d3f11b256be", "51d20eec-accb-11e9-ae20-0d3f11b256be", "52051074-accc-11e9-ae20-0d3f11b256be", "5267a2d5-accc-11e9-ae20-0d3f11b256be", "53ef5446-accc-11e9-ae20-0d3f11b256be", "559cb627-accc-11e9-ae20-0d3f11b256be", "579c1262-accd-11e9-ae20-0d3f11b256be", "589ef9a8-accc-11e9-ae20-0d3f11b256be", "5ac86959-accc-11e9-ae20-0d3f11b256be", "5c429003-accd-11e9-ae20-0d3f11b256be", "5c6e9f4a-accc-11e9-ae20-0d3f11b256be", "5e72377b-accc-11e9-ae20-0d3f11b256be", "5efa5d04-accd-11e9-ae20-0d3f11b256be", "5f5cd037-acca-11e9-ae20-0d3f11b256be", "609cb89c-accc-11e9-ae20-0d3f11b256be", "63de27fd-accc-11e9-ae20-0d3f11b256be", "65616c9e-accc-11e9-ae20-0d3f11b256be", "667ea635-accd-11e9-ae20-0d3f11b256be", "66dee4df-accc-11e9-ae20-0d3f11b256be", "69d71540-accc-11e9-ae20-0d3f11b256be", "6b24caf1-accc-11e9-ae20-0d3f11b256be", "6be726dd-accb-11e9-ae20-0d3f11b256be", "6c07c388-acca-11e9-ae20-0d3f11b256be", "6c6decc2-accc-11e9-ae20-0d3f11b256be", "6f21cce6-accd-11e9-ae20-0d3f11b256be", "710b2e39-acca-11e9-ae20-0d3f11b256be", "711cced3-accc-11e9-ae20-0d3f11b256be", "72ef4fa7-accd-11e9-ae20-0d3f11b256be", "759f0e3a-acca-11e9-ae20-0d3f11b256be", "7607b338-accd-11e9-ae20-0d3f11b256be", "76669154-accc-11e9-ae20-0d3f11b256be", "781f823e-accb-11e9-ae20-0d3f11b256be", "7bfe4cc5-accc-11e9-ae20-0d3f11b256be", "7e1eed99-accd-11e9-ae20-0d3f11b256be", "7e6a43b6-accc-11e9-ae20-0d3f11b256be", "7ed1558b-acca-11e9-ae20-0d3f11b256be", "81348747-accc-11e9-ae20-0d3f11b256be", "81a7ed4a-accd-11e9-ae20-0d3f11b256be", "82e1c218-accc-11e9-ae20-0d3f11b256be", "8473d3c9-accc-11e9-ae20-0d3f11b256be", "8605e57a-accc-11e9-ae20-0d3f11b256be", "870d87bf-accb-11e9-ae20-0d3f11b256be", "88e6702b-accc-11e9-ae20-0d3f11b256be", "8911635c-acca-11e9-ae20-0d3f11b256be", "8b02996c-accc-11e9-ae20-0d3f11b256be", "8cbed31b-accd-11e9-ae20-0d3f11b256be", "8d08a29d-accc-11e9-ae20-0d3f11b256be", "8f22f71e-accc-11e9-ae20-0d3f11b256be", "9163d1fd-acca-11e9-ae20-0d3f11b256be", "91717aff-accc-11e9-ae20-0d3f11b256be", "91c392a0-accc-11e9-ae20-0d3f11b256be", "924d5c11-accc-11e9-ae20-0d3f11b256be", "94e1dfe0-accb-11e9-ae20-0d3f11b256be", "97399ffc-accd-11e9-ae20-0d3f11b256be", "9b9bc97e-acca-11e9-ae20-0d3f11b256be", "a3e3a8ed-accd-11e9-ae20-0d3f11b256be", "a3fac5f1-accb-11e9-ae20-0d3f11b256be", "a6ab835f-acca-11e9-ae20-0d3f11b256be", "a7c075d0-acca-11e9-ae20-0d3f11b256be", "a852cac1-acca-11e9-ae20-0d3f11b256be", "adccc502-acca-11e9-ae20-0d3f11b256be", "afb41abe-accd-11e9-ae20-0d3f11b256be", "afb497d3-acca-11e9-ae20-0d3f11b256be", "b1989a14-acca-11e9-ae20-0d3f11b256be", "b1d67112-accb-11e9-ae20-0d3f11b256be", "b31527f5-acca-11e9-ae20-0d3f11b256be", "b3b138ff-accd-11e9-ae20-0d3f11b256be", "b454de93-accb-11e9-ae20-0d3f11b256be", "b4b62dc6-acca-11e9-ae20-0d3f11b256be", "b5617652-accc-11e9-ae20-0d3f11b256be", "b58c9b44-accb-11e9-ae20-0d3f11b256be", "b6887cc7-acca-11e9-ae20-0d3f11b256be", "b6b9d0a5-accb-11e9-ae20-0d3f11b256be", "b70f0a00-accd-11e9-ae20-0d3f11b256be", "b76f16e6-accb-11e9-ae20-0d3f11b256be", "b785c888-acca-11e9-ae20-0d3f11b256be", "b8bbbb27-accb-11e9-ae20-0d3f11b256be", "b8dcba63-accc-11e9-ae20-0d3f11b256be", "b9743f74-accc-11e9-ae20-0d3f11b256be", "ba038725-accc-11e9-ae20-0d3f11b256be", "ba71d956-accc-11e9-ae20-0d3f11b256be", "baf30d88-accb-11e9-ae20-0d3f11b256be", "bbbabfa9-acca-11e9-ae20-0d3f11b256be", "bcbd6d49-accb-11e9-ae20-0d3f11b256be", "bd4afc9a-acca-11e9-ae20-0d3f11b256be", "be9f1aeb-acca-11e9-ae20-0d3f11b256be", "c03d0b91-accd-11e9-ae20-0d3f11b256be", "c08b5a8c-acca-11e9-ae20-0d3f11b256be", "c1d120fd-acca-11e9-ae20-0d3f11b256be", "c2841d4e-acca-11e9-ae20-0d3f11b256be", "c3325eaf-acca-11e9-ae20-0d3f11b256be", "c3d136c0-acca-11e9-ae20-0d3f11b256be", "c41ce5c1-acca-11e9-ae20-0d3f11b256be", "ca3ba392-accd-11e9-ae20-0d3f11b256be", "cf276793-accd-11e9-ae20-0d3f11b256be", "d8b00c44-accd-11e9-ae20-0d3f11b256be", "db8bc78a-accb-11e9-ae20-0d3f11b256be", "dbddb81b-accb-11e9-ae20-0d3f11b256be", "dc77def7-accc-11e9-ae20-0d3f11b256be", "dd1668e8-accc-11e9-ae20-0d3f11b256be", "dd52b955-accd-11e9-ae20-0d3f11b256be", "ddafc2b9-accc-11e9-ae20-0d3f11b256be", "ddeca4aa-accc-11e9-ae20-0d3f11b256be", "ddef6e86-accd-11e9-ae20-0d3f11b256be", "defafdac-accb-11e9-ae20-0d3f11b256be", "e8cc8792-acca-11e9-ae20-0d3f11b256be", "e929e9d3-acca-11e9-ae20-0d3f11b256be", "e9d31a37-accd-11e9-ae20-0d3f11b256be", "ebb740b0-acc9-11e9-ae20-0d3f11b256be", "ec4c5798-accd-11e9-ae20-0d3f11b256be", "f17233ab-accc-11e9-ae20-0d3f11b256be", "f235d9b1-acc9-11e9-ae20-0d3f11b256be", "f8952db9-accd-11e9-ae20-0d3f11b256be", "fd21581d-accb-11e9-ae20-0d3f11b256be", "fd9a7fbe-accb-11e9-ae20-0d3f11b256be");

        when:
        OidContentListProto proto = OidContentListProto.newBuilder()
                .addAllOidContent(oidContentList)
                .build()

        ContentListProto response = stub.insecureFindContentListByOidContentList(proto)
        System.out.println("number of content " + response.getContentCount());
        System.out.println(response);


        then:
        response.getContentList().size() == 178
    }

}
