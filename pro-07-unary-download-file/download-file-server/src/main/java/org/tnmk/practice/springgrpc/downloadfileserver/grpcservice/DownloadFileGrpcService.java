package org.tnmk.practice.springgrpc.downloadfileserver.grpcservice;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practice.springgrpc.downloadfileserver.service.DownloadFileService;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileGrpcServiceGrpc;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileRequestProto;
import org.tnmk.practice.springgrpc.protobuf.DownloadFileResponseProto;

@GRpcService
public class DownloadFileGrpcService extends DownloadFileGrpcServiceGrpc.DownloadFileGrpcServiceImplBase {

    @Autowired
    private DownloadFileService downloadFileService;

    @Override
    public void downloadFile(DownloadFileRequestProto request, StreamObserver<DownloadFileResponseProto> responseObserver) {
        byte[] bytes = downloadFileService.getFileData(request.getFileName());

        DownloadFileResponseProto downloadFileResponseProto = DownloadFileResponseProto.newBuilder().setData(ByteString.copyFrom(bytes)).build();
        responseObserver.onNext(downloadFileResponseProto);
        responseObserver.onCompleted();
    }
}
