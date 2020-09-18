package org.tnmk.practice.springgrpc.pro04serverinterceptorheaderanderror.samplestory.grpcservice;

import org.tnmk.practice.authentication.authenticationserviceproto.proto.AuthenticationGrpcServiceGrpc;
import org.tnmk.practice.authentication.authenticationserviceproto.proto.AuthenticationRequestProto;
import org.tnmk.practice.authentication.authenticationserviceproto.proto.AuthenticationResponseProto;
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
