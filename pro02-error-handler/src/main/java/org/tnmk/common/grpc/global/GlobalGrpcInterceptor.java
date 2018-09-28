package org.tnmk.common.grpc.global;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.tnmk.common.grpc.support.HeaderConstants;
import org.tnmk.common.grpc.support.MetadataUtils;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

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
    private final ExceptionTranslator exceptionTranslator;

    @Autowired
    public GlobalGrpcInterceptor(ExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> serverCallHandler) {
        try {
            String correlationId = MetadataUtils.getStringValue(headers, HeaderConstants.CORRELATION_ID);
            if (StringUtils.isEmpty(correlationId)) {
                correlationId = UUID.randomUUID().toString();
                logger.debug(String.join(" ", "Cannot get correlationId from header"
                    , "header", String.valueOf(headers)
                    , "newCorrelationId", correlationId
                    , "caller", call.getMethodDescriptor().toString()
                    )
                );
            }
            logger.debug(String.join(" ", "Receive request "
                , "header", String.valueOf(headers)
                , "correlationId", correlationId
                , "caller", call.getMethodDescriptor().toString()
                )
            );
            MDC.put(MDCConstants.CORRELATION_ID, correlationId);
            ServerCall.Listener<I> listener = serverCallHandler.startCall(call, headers);
            return listener;
        } catch (Exception ex) {
            // The actual error is already catch and return Error State by GlobalGrpcAdvice.
            // This is just for safety protection, maybe someday we decide not to use GlobalGrpcAdvice anymore, this code is still fine.
            Status status = exceptionTranslator.translateException(ex);
            logger.error("Error in gRPC Endpoint. Cause: " + ex.getMessage(), ex);
            call.close(status, headers);
            return NOOP_LISTENER;
        }
    }
}