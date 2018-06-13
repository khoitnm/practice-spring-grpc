package org.tnmk.common.grpc.errorhandler;

import io.grpc.Status;

public interface ExceptionTranslator {
    Status translateException(Throwable ex);
}
