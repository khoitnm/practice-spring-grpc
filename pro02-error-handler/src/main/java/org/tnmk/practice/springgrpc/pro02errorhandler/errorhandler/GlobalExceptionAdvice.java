package org.tnmk.practice.springgrpc.pro02errorhandler.errorhandler;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class GlobalExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    private final ExceptionToGrpcStatusTranslator exceptionTranslator;

    @Autowired
    public GlobalExceptionAdvice(ExceptionToGrpcStatusTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    /**
     * The PointCuts are all classes with annotation {@link org.lognet.springboot.grpc.GRpcService} and the methods with one argument {@link StreamObserver}.
     * <p>
     * View more about point cut expression at https://www.eclipse.org/aspectj/doc/next/progguide/language-joinPoints.html
     *
     * @param joinPoint
     */
    //    @Around("execution(void org.tnmk.practice..*Resource.* (*, io.grpc.stub.StreamObserver))")
    @Around("within(@org.lognet.springboot.grpc.GRpcService *) && execution(void *..*.* (*, io.grpc.stub.StreamObserver))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();
        try {
            joinPoint.proceed(arguments);
        } catch (Throwable throwable) {
            StreamObserver<?> streamObserver = findResultArgument(arguments);
            if (streamObserver == null) {
                LOGGER.error("Cannot find streamObserver argument in the GRPCService's method. " +
                    "The method is: {}", joinPoint.getSignature().toString());
                throw throwable;
            } else {
                Status status = exceptionTranslator.translateException(throwable);
                streamObserver.onError(status.asException());
                LOGGER.error("Error in gRPC Endpoint method: {}. {}", joinPoint.getSignature().toString(), status, throwable);
            }
        }
    }

    private StreamObserver<?> findResultArgument(Object[] joinPointArguments) {
        for (Object joinPointArgument : joinPointArguments) {
            if (joinPointArgument instanceof StreamObserver) {
                return (StreamObserver) joinPointArgument;
            }
        }
        return null;
    }
}
