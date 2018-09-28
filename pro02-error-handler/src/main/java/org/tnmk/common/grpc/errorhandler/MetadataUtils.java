package org.tnmk.common.grpc.errorhandler;

import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class MetadataUtils {
    public static String getStringValue(Metadata metadata, String key){
        return metadata.get(Metadata.Key.of(key, ASCII_STRING_MARSHALLER));
    }

    public static void putStringValue(Metadata metadata, String key, String value){
        metadata.put(Metadata.Key.of(key, ASCII_STRING_MARSHALLER), value);
    }
}
