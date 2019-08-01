package com.leonardo.monalisa.contentmanagement.content.contentsyndicationgrpcservice

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.AssetProto
import com.leonardo.monalisa.contentmanagement.content.proto.AssetTypeProto
import com.leonardo.monalisa.contentmanagement.content.proto.ContentListProto
import com.leonardo.monalisa.contentmanagement.content.proto.ContentProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.EmptyProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.HotelViewIdProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.OidContentProto
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory
import spock.lang.Ignore

import java.util.stream.Collectors

class ContentVscapeSyndicationGrpcServiceSpec extends BaseSpecification {

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

    def 'Verify find content by Hotelview and Oid account'() {
        given:
        // 819d4fdb-7894-4ede-b113-69eddc414d01 : Lux
        // 91496704-821d-11e9-97da-270517923c8b : Langham
        UUID oidAccount = UUID.fromString("819d4fdb-7894-4ede-b113-69eddc414d01");
        String IPID = "HLR"

        when:
        HotelViewIdProto request = HotelViewIdProto.newBuilder()
                .setOidAccount(oidAccount.toString())
                .setHotelViewId(IPID)
                .build()
        ContentListProto response = stub.findContentByHotelViewId(request)
        System.out.println("number of content " + response.getContentCount());

        String oidContentList = "\"" + response.contentList.stream().map { contentProto -> contentProto.oidContent }
                .collect(Collectors.joining("\",\"")) + "\"";
        System.out.println(oidContentList);

        String contentList = response.contentList.stream().map { contentProto ->
            "oidContent: " + contentProto.oidContent + "\t" + contentProto.getAsset().getAssetType() + "\t encodings: " + countEncodings(contentProto.getAsset()) + "\t" + contentProto.getVscapeVmmFilePath()
        }.collect(Collectors.joining("\n"));
        System.out.println(contentList);

        System.out.println("Full Content"+response);

        then:
        response.getContentList().size() > 0
    }

    private int countEncodings(AssetProto assetProto) {
        AssetTypeProto assetTypeProto = assetProto.getAssetType();
        if (assetTypeProto == AssetTypeProto.IMAGE_ASSET) {
            return assetProto.getImageAssetProto().getEncodingsCount();
        } else if (assetTypeProto == AssetTypeProto.VIDEO_ASSET) {
            return assetProto.getVideoAssetProto().getVideoEncodingsCount()
        } else if (assetTypeProto == AssetTypeProto.VIRTUAL_TOUR_ASSET) {
            return assetProto.getVirtualTourAssetProto().getEncodingsCount()
        } else {
            return -1;
        }
    }

    def 'Find Content'() {
        given:
        String oidContent = "79afe91f-b2e4-11e9-a636-fb56bb11282f";

        when:
        OidContentProto oidContentProto = OidContentProto.newBuilder().setOidContent(oidContent).build();
        ContentProto contentProto = stub.findContentByOidContent(oidContentProto)
        System.out.println("Content: \n" + contentProto);
        then:
        noExceptionThrown()
    }

    @Ignore
    def 'Delete Content'() {
        given:
        String oidContent = "46553c3f-afd0-11e9-a524-612230a08ad8";

        when:
        OidContentProto oidContentProto = OidContentProto.newBuilder().setOidContent(oidContent).build();
        EmptyProto response = stub.deleteByOidContent(oidContentProto)
        System.out.println("Successful delete Content " + oidContent);
        then:
        noExceptionThrown()
    }

}
