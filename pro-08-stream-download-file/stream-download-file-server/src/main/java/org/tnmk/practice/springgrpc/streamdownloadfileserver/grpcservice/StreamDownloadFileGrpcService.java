package org.tnmk.practice.springgrpc.streamdownloadfileserver.grpcservice;

import com.google.protobuf.ByteString;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.common.utils.UnexpectedException;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadChunkProto;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileRequestProto;
import org.tnmk.practice.springgrpc.streamdownloadfileserver.service.StreamDownloadFileService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;

@GRpcService
public class StreamDownloadFileGrpcService extends StreamDownloadFileGrpcServiceGrpc.StreamDownloadFileGrpcServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int BUFFER_SIZE = 16 * 1024;//16 KB
    @Autowired
    private StreamDownloadFileService streamDownloadFileService;

    @Override
    public void downloadFile(StreamDownloadFileRequestProto request, StreamObserver<StreamDownloadChunkProto> responseObserver) {
        ServerCallStreamObserver<StreamDownloadChunkProto> serverCallStreamObserver = (ServerCallStreamObserver<StreamDownloadChunkProto>) responseObserver;

        InputStream inputStream = streamDownloadFileService.getFileData(request.getFileName());

        // Using a Ready Handler allows the server to back off when the client is overwhelmed with messages
        serverCallStreamObserver.setOnReadyHandler(() -> {
            transferDataFromInputStreamToGrpcResponse(inputStream, serverCallStreamObserver);
        });

        // Use a cancel handler to stop download process when cancelled by the gRPC client
        serverCallStreamObserver.setOnCancelHandler(() -> {
            stopReadingDataFromInputStream(inputStream, serverCallStreamObserver);
        });
    }

    private void stopReadingDataFromInputStream(InputStream inputStream, ServerCallStreamObserver<StreamDownloadChunkProto> serverCallStreamObserver) {
        logger.info("Bulk download was cancelled by client");
        try {
            inputStream.close();
        } catch (IOException e) {
            logger.error("Failed to clean bulk download streams: " + e.getMessage(), e);
        }
        serverCallStreamObserver.onCompleted();
    }

    private void transferDataFromInputStreamToGrpcResponse(InputStream inputStream, ServerCallStreamObserver<StreamDownloadChunkProto> serverCallStreamObserver) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            //The number of bytes which will be read from the inputStream and stored into the buffer.
            int numReadBytes = 0;
            while (serverCallStreamObserver.isReady() && (numReadBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
                StreamDownloadChunkProto dataChunkProto = StreamDownloadChunkProto.newBuilder()
                        .setData(ByteString.copyFrom(buffer, 0, numReadBytes))
                        .build();
                serverCallStreamObserver.onNext(dataChunkProto);
            }

            // If loop terminated because of length, the bulk download is complete; otherwise simply wait until client is ready again
            if (numReadBytes == -1) {//when numReadBytes == -1, it means all data inside the InputStream are read completely.
                close(inputStream);
                serverCallStreamObserver.onCompleted();
            } else {
                //simply wait until client is ready again.
            }
        } catch (Exception e) {
            close(inputStream);
            serverCallStreamObserver.onCompleted();
            throw new UnexpectedException("Cannot read data and transfer it into the response " + e.getMessage(), e);
        }
    }


    private void close(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new UnexpectedException("Cannot close the input stream " + e.getMessage(), e);
        }
    }
}
