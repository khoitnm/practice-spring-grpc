package org.tnmk.common.grpc.global;

import io.grpc.*;
import io.grpc.stub.MetadataUtils;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.tnmk.common.grpc.global.MDCConstants.CORRELATION_ID;

/**
 * This class is processed before calling ProtoBuf Resource layer.
 * Note: To make this class works in UnitTest, you must register ServerInterceptor into {@link GrpcServerRule} or {@link GrpcCleanupRule}.
 * Please view more at {@link SampleItemGrpcServiceTest}.
 * <p>
 * You can use AOP ({@link GlobalGrpcAdvice} to handle all exception) without needing this class.<br/>
 * However, this class helps you to get headers data, which could be very useful in some cases.
 */
@GRpcGlobalInterceptor
public class GlobalGrpcServerInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {
    };

    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> serverCallHandler) {
        try {
            String correlationId = UUID.randomUUID().toString();
            // If we just use MDC.put(...) here, in grpcService layer, we cannot get data from MDC because they are on different threads.
            // https://github.com/grpc/grpc-java/issues/2280
            // That's why we need to copy the MDC's contextMap to grpcService's thread.
            MDC.put(CORRELATION_ID, correlationId);

            logger.info("GrpcInterceptor. newCorrelationId: {}", correlationId);
            ServerCall.Listener<I> originalListener = serverCallHandler.startCall(call, headers);
            ServerCall.Listener<I> forwardListener = createForwardContextListener(originalListener, MDC.getCopyOfContextMap());
            return forwardListener;
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

    private <ReqT> ServerCall.Listener<ReqT> createForwardContextListener(ServerCall.Listener<ReqT> original, Map<String, String> mdcContext) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(original) {
            @Override
            public void onMessage(final ReqT message) {
                MDC.setContextMap(mdcContext);
                super.onMessage(message);
            }

            @Override
            public void onHalfClose() {
                MDC.setContextMap(mdcContext);
                super.onHalfClose();
            }

            @Override
            public void onCancel() {
                MDC.setContextMap(mdcContext);
                super.onCancel();
            }

            @Override
            public void onComplete() {
                MDC.setContextMap(mdcContext);
                super.onComplete();
            }

            @Override
            public void onReady() {
                MDC.setContextMap(mdcContext);
                super.onReady();
            }
        };
    }
}