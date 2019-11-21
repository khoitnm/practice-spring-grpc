package org.tnmk.practice.springgrpc.streamdownloadfileserver.grpcservice;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.streamdownloadfileserver.service.StreamDownloadFileService;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileRequestProto;
import org.tnmk.practice.springgrpc.protobuf.StreamDownloadFileResponseProto;

@GRpcService
public class StreamDownloadFileGrpcService extends StreamDownloadFileGrpcServiceGrpc.StreamDownloadFileGrpcServiceImplBase {

    @Autowired
    private StreamDownloadFileService streamDownloadFileService;

    @Override
    public void downloadFile(StreamDownloadFileRequestProto request, StreamObserver<StreamDownloadFileResponseProto> responseObserver) {
        byte[] bytes = streamDownloadFileService.getFileData(request.getFileName());

        StreamDownloadFileResponseProto streamDownloadFileResponseProto = StreamDownloadFileResponseProto.newBuilder().setData(ByteString.copyFrom(bytes)).build();
        responseObserver.onNext(streamDownloadFileResponseProto);
        responseObserver.onCompleted();
    }
}
