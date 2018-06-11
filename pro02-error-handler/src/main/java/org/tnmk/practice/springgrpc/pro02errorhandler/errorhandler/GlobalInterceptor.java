package org.tnmk.practice.springgrpc.pro02errorhandler.errorhandler;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.stereotype.Service;

@Service
@GRpcGlobalInterceptor
public  class GlobalInterceptor implements ServerInterceptor {
    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> next) {

        return next.startCall(call, headers);
    }
    // ommited
}