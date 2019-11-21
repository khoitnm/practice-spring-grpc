package org.tnmk.practice.springgrpc.client.samplestory.streamdownloadfileclient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileRequestProto;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileResponseProto;

import java.lang.invoke.MethodHandles;

import static org.apache.commons.io.Charsets.UTF_8;

@Service
public class StreamDownloadFileClient {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ManagedChannel channel;
    private StreamDownloadFileGrpcServiceGrpc.StreamDownloadFileGrpcServiceBlockingStub blockingStub;

    @Autowired
    public StreamDownloadFileClient(GrpcConnectionProperties grpcConnectionProperties) {
        this.channel = ManagedChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .usePlaintext()
            .build();
        this.blockingStub = StreamDownloadFileGrpcServiceGrpc.newBlockingStub(channel);
    }

    public void streamDownloadFileFromServer() {
        StreamDownloadFileRequestProto streamDownloadFileRequestProto = StreamDownloadFileRequestProto.newBuilder()
            .setFileName("RandomFile_" + System.nanoTime())
            .build();
        StreamDownloadFileResponseProto streamDownloadFileResponseProto = blockingStub.streamDownloadFile(streamDownloadFileRequestProto);
        String fileContent = streamDownloadFileResponseProto.getData().toString(UTF_8);
        logger.info("File Content\n" + fileContent);
    }
}
