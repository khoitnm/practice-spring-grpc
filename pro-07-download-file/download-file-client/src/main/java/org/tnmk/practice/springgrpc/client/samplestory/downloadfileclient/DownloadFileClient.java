package org.tnmk.practice.springgrpc.client.samplestory.downloadfileclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileRequestProto;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileResponseProto;

import java.lang.invoke.MethodHandles;

import static org.apache.commons.io.Charsets.UTF_8;

@Service
public class DownloadFileClient {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ManagedChannel channel;
    private DownloadFileGrpcServiceGrpc.DownloadFileGrpcServiceBlockingStub blockingStub;

    @Autowired
    public DownloadFileClient(GrpcConnectionProperties grpcConnectionProperties) {
        this.channel = ManagedChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .usePlaintext()
            .build();
        this.blockingStub = DownloadFileGrpcServiceGrpc.newBlockingStub(channel);
    }

    public void downloadFileFromServer() {
        DownloadFileRequestProto downloadFileRequestProto = DownloadFileRequestProto.newBuilder()
            .setFileName("RandomFile_" + System.nanoTime())
            .build();
        DownloadFileResponseProto downloadFileResponseProto = blockingStub.downloadFile(downloadFileRequestProto);
        String fileContent = downloadFileResponseProto.getData().toString(UTF_8);
        logger.info("File Content\n" + fileContent);
    }
}
