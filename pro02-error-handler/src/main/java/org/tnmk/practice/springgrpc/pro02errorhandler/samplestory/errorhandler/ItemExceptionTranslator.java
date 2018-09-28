package org.tnmk.practice.springgrpc.pro02errorhandler.samplestory.errorhandler;

import io.grpc.Status;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.global.ExceptionTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class translate an exception to a specific gRPC {@link Status}.
 */
@Component
public class ItemExceptionTranslator implements ExceptionTranslator {
    /**
     * The map between exception types and the function for converting those exception.<br/>
     * <p/>
     * <b>The key</b> is the exception type.<br/>
     * <p/>
     * <b>The value</b> is the conversion function of those exception.<br/>
     * <li>Input: the exception</li>
     * <li>Output: the gRPC Status</li>
     */
    private static final Map<Class<? extends Throwable>, Function<Throwable, Status>> EXCEPTION_CONVERTERS_MAP = constructSpecificConvertersForExceptionTypes();

    /**
     * Any exception which is not defined in this map will be treated as a general Exception and the respective status is {@link Status#INTERNAL} error.
     * View more in {@link #getExceptionConverter(Throwable)}.
     * <p>
     * So you don't have to declare general {@link Exception} or {@link Throwable} in this map anymore.
     *
     * @return
     */
    private static Map<Class<? extends Throwable>, Function<Throwable, Status>> constructSpecificConvertersForExceptionTypes() {
        Map<Class<? extends Throwable>, Function<Throwable, Status>> result = new HashMap<>();
        result.put(
            IllegalArgumentException.class,
            throwable -> Status.INVALID_ARGUMENT.withDescription(throwable.getMessage()).withCause(throwable));
        //TODO In the future, we may need to handle Status.PERMISSION_DENIED for security...
        return result;
    }

    @Override
    public Status translateException(Throwable ex) {
        Function<Throwable, Status> converter = getExceptionConverter(ex);
        return converter.apply(ex);
    }

    /**
     * @param ex
     * @return this method never return null.
     */
    private Function<Throwable, Status> getExceptionConverter(Throwable ex) {
        Function<Throwable, Status> converter = EXCEPTION_CONVERTERS_MAP.get(ex.getClass());
        if (converter == null) {
            return getDefaultInternalErrorConverter();
        } else {
            return converter;
        }
    }

    private Function<Throwable, Status> getDefaultInternalErrorConverter() {
        return throwable -> Status.INTERNAL.withCause(throwable).withDescription(throwable.getMessage());
    }
}
