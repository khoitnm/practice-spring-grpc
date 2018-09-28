package org.tnmk.common.grpc.global;

import io.grpc.Status;

public interface ExceptionTranslator {
    Status translateException(Throwable ex);
}
