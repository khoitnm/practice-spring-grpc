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
    private StreamDownloadFileService streamStreamDownloadFileService;

    @Override
    public void streamStreamDownloadFile(StreamDownloadFileRequestProto request, StreamObserver<StreamDownloadFileResponseProto> responseObserver) {
        byte[] bytes = streamStreamDownloadFileService.getFileData(request.getFileName());

        StreamDownloadFileResponseProto streamStreamDownloadFileResponseProto = StreamDownloadFileResponseProto.newBuilder().setData(ByteString.copyFrom(bytes)).build();
        responseObserver.onNext(streamStreamDownloadFileResponseProto);
        responseObserver.onCompleted();
    }
}
