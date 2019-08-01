package org.tnmk.practice.springgrpc.pro04serverinterceptorheaderanderror.samplestory.errorhandler;

import io.grpc.Status;
import org.springframework.stereotype.Component;
import org.tnmk.common.grpc.global.ExceptionTranslator;
import org.tnmk.common.grpc.support.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
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

    protected Status createStatusFromException(Throwable throwable, Status status) {
        return status.withCause(throwable).withDescription(constructExceptionDescriptionWithFieldsData(throwable));
    }

    //TODO should move this to the common library.
    /**
     * This method will return a description with all information inside the exception's fields.
     * You don't have to construct a nice looking exception description by yourself.
     *
     * For example:
     * <pre>
     * You have an Exception class:
     * public class MyException {
     *     private String fieldA
     *     private String fieldB
     * }
     *
     * In some of your code, you will throw an exception:
     * throw new MyException("Some thing wrong in my application.", fieldAValue, fieldBValue);
     *
     * Then this method will write the description message like this:
     * "Some thing wrong in my application. fieldA: fieldAValue, fieldB: fieldBValue"
     *
     * You don't need to write a pretty description when creating your exception instance like this:
     * throw new MyException("Some thing wrong in my application. fieldA: "+ fieldAValue +", fieldB: "+fieldBValue, fieldAValue, fieldBValue);
     * </pre>
     * @param throwable
     * @return
     */
    protected String constructExceptionDescriptionWithFieldsData(Throwable throwable){
        StringJoiner stringJoiner = new StringJoiner(", ");
        stringJoiner.add(throwable.getMessage());
        StringBuilder description = new StringBuilder(throwable.getMessage());
        Map<String, Object> fieldValues = ReflectionUtils.getPropertyValuesOfBean(throwable, RuntimeException.class);
        for (Map.Entry<String, Object> entry : fieldValues.entrySet()) {
            stringJoiner.add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        return description.toString();
    }

    private Function<Throwable, Status> getDefaultInternalErrorConverter() {
        return throwable -> createStatusFromException(throwable, Status.INTERNAL);
    }
}
