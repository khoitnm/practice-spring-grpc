package org.tnmk.practice.springgrpc.pro04serverinterceptorheaderanderror.samplestory.grpcservice;

import com.leonardo.monalisa.authentication.authenticationserviceproto.proto.AuthenticationGrpcServiceGrpc;
import com.leonardo.monalisa.authentication.authenticationserviceproto.proto.AuthenticationRequestProto;
import com.leonardo.monalisa.authentication.authenticationserviceproto.proto.AuthenticationResponseProto;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

/**
 * This class is just used for demo we can have many @GRpcService classes in the same project.
 */
@GRpcService
public class AuthenticationGrpcService extends AuthenticationGrpcServiceGrpc.AuthenticationGrpcServiceImplBase {
    @Override
    public void authenticate(AuthenticationRequestProto request, StreamObserver<AuthenticationResponseProto> responseObserver) {
        responseObserver.onCompleted();
    }
}
