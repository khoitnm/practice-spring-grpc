package org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.leo.monalisa.assetencoding.api.AssetEncodingResponse;
import com.leo.monalisa.assetencoding.api.EncodingResponse;
import com.leonardo.monalisa.authentication.authenticationserviceproto.proto.AuthenticationRequestProto;
import com.leonardo.monalisa.authentication.authenticationserviceproto.proto.Sample;
import org.junit.Test;

import java.util.Base64;

public class GrpcConverterTest {

    @Test
    public void test(){
//        String base64String = "ChJjb250ZW50LW1hbmFnZW1lbnQSJDAwMDAxMWU5LWZmZjctY2M2ZS05MzMxLTMxODUwZWVhMDA1YxokYTAyMGExNjQtZmZjYS00OTRhLWEyNTktYmE0NjlhZjFiN2NmSm8KE0ludmFsaWRSZXF1ZXN0RXJyb3ISWGVuY29kaW5nIHJlcXVlc3QgaGFzIGludmFsaWQgZmllbGRzOiBJbWFnZUVuY29kaW5nUmVxdWVzdDogbWlzc2luZyBmaWVsZHM6IGhlaWdodCwgd2lkdGg=";

        String base64String = "ChJjb250ZW50LW1hbmFnZW1lbnQSJDAwMDAxMWU5LWVmYmItMDM4MC05MzMxLTMxODUwZWVhMDA1YxokMjZjYTc1ODktNGM0MC00ZmM1LWFhZTUtYzIxZTBiODA1ODUySm8KE0ludmFsaWRSZXF1ZXN0RXJyb3ISWGVuY29kaW5nIHJlcXVlc3QgaGFzIGludmFsaWQgZmllbGRzOiBJbWFnZUVuY29kaW5nUmVxdWVzdDogbWlzc2luZyBmaWVsZHM6IGhlaWdodCwgd2lkdGg=";
        byte[] bytes = Base64.getDecoder().decode(base64String);

        try {
            EncodingResponse.parser().parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
