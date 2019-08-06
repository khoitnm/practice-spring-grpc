package org.tnmk.common.grpc.global;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import java.util.Map;

import static org.tnmk.common.grpc.global.MDCConstants.CORRELATION_ID;

public class MdcContextResponseServerCall<I, O> extends ForwardingServerCall.SimpleForwardingServerCall<I, O> {
    private final Logger logger = LoggerFactory.getLogger(MdcContextResponseServerCall.class);

    private final Map<String, String> mdcContext;

    public MdcContextResponseServerCall(ServerCall<I, O> delegate, Map<String, String> mdcContext) {
        super(delegate);
        this.mdcContext = mdcContext;
    }

    @Override
    public void sendHeaders(Metadata responseHeaders) {
        //In a real application, we hardly ever need to set correlationId back to response.
        //This is just an example how to set value from Mdc and set it into response headers.
        MDC.setContextMap(mdcContext);
        String correlationId = MDC.get(CORRELATION_ID);
        MetadataUtils.putMetadata(responseHeaders, CORRELATION_ID, correlationId);
        super.sendHeaders(responseHeaders);
    }
}
