package org.tnmk.common.grpc.support;

import io.grpc.Metadata;
import io.grpc.stub.AbstractStub;

import java.util.HashMap;
import java.util.Map;

public class MetadataUtils {
    public static String getStringValue(Metadata metadata, String key){
        return metadata.get(constructKey(key));
    }

    public static void putMetadata(Metadata metadata, String key, String value){
        metadata.put(constructKey(key), value);
    }

    public static Metadata constructMetadata(Map<String, String> metadataMap) {
        Metadata metadata = new Metadata();
        for (Map.Entry<String, String> entry : metadataMap.entrySet()) {
            metadata.put(constructKey(entry.getKey()), entry.getValue());
        }
        return metadata;
    }

    public static Metadata.Key<String> constructKey(String key) {
        return Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
    }

    public static <T extends AbstractStub<T>> T attachHeaders(T stub, String correlationId) {
        Map<String, String> metadataMap = new HashMap<>();
        metadataMap.put(HeaderConstants.CORRELATION_ID, correlationId);
        Metadata metadata = MetadataUtils.constructMetadata(metadataMap);
        return io.grpc.stub.MetadataUtils.attachHeaders(stub, metadata);
    }
}
