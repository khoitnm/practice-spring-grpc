package org.tnmk.common.grpc.global;

import io.grpc.ForwardingServerCallListener;
import io.grpc.ServerCall;
import org.slf4j.MDC;

import java.util.Map;

/**
 * If we just use MDC.put(...) in {@link GlobalGrpcServerInterceptor}, in grpcService layer, we cannot get data from MDC because they are on different threads.
 * https://github.com/grpc/grpc-java/issues/2280
 * That's why we need to copy the MDC's contextMap to grpcService's thread by using this class.
 * Please see an example how to use this class in {@link GlobalGrpcServerInterceptor}
 */
public class MdcContextForwardingServerCallListener<ReqT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {
    private final Map<String, String> mdcContext;

    public MdcContextForwardingServerCallListener(ServerCall.Listener<ReqT> original, Map<String, String> mdcContext) {
        super(original);
        this.mdcContext = mdcContext;
    }

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
}
