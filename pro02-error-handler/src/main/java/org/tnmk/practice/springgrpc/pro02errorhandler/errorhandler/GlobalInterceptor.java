package org.tnmk.practice.springgrpc.pro02errorhandler.errorhandler;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class is processed before calling ProtoBuf Resource layer.
 */
@Service
@GRpcGlobalInterceptor
public class GlobalInterceptor implements ServerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalInterceptor.class);

    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> serverCallHandler) {
        try {
            ServerCall.Listener<I> listener = serverCallHandler.startCall(call, headers);
            //When starting, the call will be started in another thread, so this method will be continued immediately, and we cannot get response from here.
            LOGGER.info("LISTENER: " +
                "\n\theader: " + headers +
                "\n\tcall: " + call +
                "\n\tattributes: " + call.getAttributes());
            return listener;
        } catch (Exception ex) {
            //Because the caller was started in another thread, if there's any error, this method also could not able to catch that exception!
            LOGGER.error("LISTENER: ERROR: header: {}, servercall: {}, exception: {}", headers, call, ex.getMessage(), ex);
            call.close(translateException(ex), headers);
            return new ServerCall.Listener<I>() {
            };
        }
    }

    private Status translateException(Exception ex) {
        return Status.INTERNAL.withCause(ex).withDescription(ex.getMessage());
    }
}