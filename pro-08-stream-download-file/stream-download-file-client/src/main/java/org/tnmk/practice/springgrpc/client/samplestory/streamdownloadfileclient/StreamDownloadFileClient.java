package org.tnmk.practice.springgrpc.client.samplestory.streamdownloadfileclient;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.common.grpc.client.GrpcConnectionProperties;
import org.tnmk.common.utils.UnexpectedException;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadChunkProto;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileRequestProto;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;

@Service
public class StreamDownloadFileClient {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String DOWNLOADED_FILE_PATH = "downloaded_file.txt";
    private final WriteStreamDataToFileService writeStreamDataToFileService;
    private final StreamDownloadFileGrpcServiceGrpc.StreamDownloadFileGrpcServiceBlockingStub blockStub;

    @Autowired
    public StreamDownloadFileClient(GrpcConnectionProperties grpcConnectionProperties, WriteStreamDataToFileService writeStreamDataToFileService) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
            .usePlaintext()
            .build();
        this.writeStreamDataToFileService = writeStreamDataToFileService;
        this.blockStub = StreamDownloadFileGrpcServiceGrpc.newBlockingStub(channel);
    }

    public void downloadFileFromServer() {
        String filePath = DOWNLOADED_FILE_PATH;
        StreamDownloadFileRequestProto streamDownloadFileRequestProto = StreamDownloadFileRequestProto.newBuilder()
            .setFileName("RandomFile_" + System.nanoTime())
            .build();
        Iterator<StreamDownloadChunkProto> streamDownloadChunkProtoIterator = blockStub.downloadFile(streamDownloadFileRequestProto);
        OutputStream outputStream = writeStreamDataToFileService.createStreamForWritingDataToFile(filePath);
        while (streamDownloadChunkProtoIterator.hasNext()) {
            StreamDownloadChunkProto streamDownloadChunkProto = streamDownloadChunkProtoIterator.next();
            ByteString byteString = streamDownloadChunkProto.getData();
            writeStreamDataToFileService.writeBytesDataToFile(byteString.toByteArray(), outputStream);
        }
        try {
            outputStream.close();
            logger.info("Finish downloading bytes, the data was written into the file " + filePath);
        } catch (IOException e) {
            throw new UnexpectedException("Cannot close outputStream:" + e.getMessage(), e);
        }
    }
}
