package org.tnmk.practice.springgrpc.client.samplestory.streamdownloadzipfilesclient;

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
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadZipFilesGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadZipFilesRequestProto;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;

@Service
public class StreamDownloadZipFilesClient {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String DOWNLOADED_FILE_PATH = "downloaded_file.zip";
    private final WriteStreamDataToFileService writeStreamDataToFileService;
    private final StreamDownloadZipFilesGrpcServiceGrpc.StreamDownloadZipFilesGrpcServiceBlockingStub blockStub;

    @Autowired
    public StreamDownloadZipFilesClient(GrpcConnectionProperties grpcConnectionProperties, WriteStreamDataToFileService writeStreamDataToFileService) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcConnectionProperties.getHost(), grpcConnectionProperties.getPort())
                .usePlaintext()
                .build();
        this.writeStreamDataToFileService = writeStreamDataToFileService;
        this.blockStub = StreamDownloadZipFilesGrpcServiceGrpc.newBlockingStub(channel);
    }

    public void downloadFileFromServer() {
        String filePath = DOWNLOADED_FILE_PATH;
        StreamDownloadZipFilesRequestProto streamDownloadZipFilesRequestProto = StreamDownloadZipFilesRequestProto.newBuilder()
                .setNumZipFiles(10)
                .build();
        Iterator<StreamDownloadChunkProto> streamDownloadChunkProtoIterator = blockStub.downloadFile(streamDownloadZipFilesRequestProto);
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
