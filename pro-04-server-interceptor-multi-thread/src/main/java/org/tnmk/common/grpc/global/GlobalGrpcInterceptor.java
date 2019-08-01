package org.tnmk.common.grpc.global;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * This class is processed before calling ProtoBuf Resource layer.
 * Note: it seems that this class doesn't work with component test, but only work when really deployed as a grpc server.
 * You can use AOP ({@link GlobalGrpcAdvice} to handle all exception) without needing this class.<br/>
 * However, this class helps you to get headers data, which could be very useful in some cases.
 */
@GRpcGlobalInterceptor
public class GlobalGrpcInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
    };

    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> serverCallHandler) {
        try {
            logger.info("Delegate message to call: {}", call.getMethodDescriptor());
            /**
             * NOTE:
             * serverCallHandler.startCall() doesn't really trigger the code in GrpcService layer yet.
             * It's only triggered after this method is finished (could be in another thread).
             */
            ServerCall.Listener<I> listener = serverCallHandler.startCall(call, headers);
            return listener;
        } catch (Exception ex) {
            /**
             * Actually, this catch cannot catch any exception thrown by GrpcService layer because it's not triggered yet.
             * This try-catch is only able to catch exception when creating the listener, not when triggering the listener.
             * The real exception handler is implemented inside GlobalGrpcServerAdvice.
             */
            Status status = Status.UNKNOWN.withCause(ex).withDescription(ex.getMessage());
            logger.error("Error in gRPC Endpoint. Cause: " + ex.getMessage(), ex);
            call.close(status, headers);
            return NOOP_LISTENER;
        }
    }
}