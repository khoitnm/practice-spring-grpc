package org.tnmk.common.grpc.client;

import io.grpc.*;
import java.lang.invoke.MethodHandles;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.support.HeaderConstants;
import org.tnmk.common.grpc.support.MetadataUtils;
import org.tnmk.practice.springgrpc.grpcclientapp.common.MDCConstants;

@Component
public class GlobalGrpcClientInterceptor implements ClientInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//    @VisibleForTesting
//    static final Metadata.Key<String> CUSTOM_HEADER_KEY = Metadata.Key.of("custom_client_header_key", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> CORRELATION_ID = MetadataUtils.constructKey(HeaderConstants.CORRELATION_ID);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                /* put custom header */
                String correlationId = MDC.get(MDCConstants.CORRELATION_ID);
                if (correlationId == null){
                    correlationId = UUID.randomUUID().toString();
                }
                headers.put(CORRELATION_ID, correlationId);
                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        /**
                         * if you don't need receive header from server,
                         * you can use {@link io.grpc.stub.MetadataUtils#attachHeaders}
                         * directly to send header
                         */
                        logger.info("header received from server:" + headers);
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}