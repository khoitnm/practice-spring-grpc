package org.tnmk.practice.springgrpc.pro02errorhandler.errorhandler;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    /**
     * View more at https://www.eclipse.org/aspectj/doc/next/progguide/language-joinPoints.html or
     *
     * @param joinPoint
     */
    //    @Around("execution(void org.tnmk.practice..*Resource.* (*, io.grpc.stub.StreamObserver))")
    @Around("within(@org.lognet.springboot.grpc.GRpcService *) && execution(void *..*.* (*, io.grpc.stub.StreamObserver))")
    public void around(ProceedingJoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        try {
            LOGGER.info("AOP: before executing joinPoint {}", joinPoint);
            joinPoint.proceed(arguments);
            LOGGER.info("AOP: after executing joinPoint {}", joinPoint);
        } catch (Throwable throwable) {
            LOGGER.error("AOP: ERROR: " + throwable.getMessage(), throwable);
            if (arguments.length != 2) {
                LOGGER.error("AOP: The arguments for a gRPC Resource should be 2, while the actual number of arguments is " + arguments.length, throwable);
            } else {
                Object secondArgument = arguments[1];
                if (!(secondArgument instanceof StreamObserver)) {
                    LOGGER.error("AOP: The second of a gRPC Resource should be should be an instance of StreamObserver, while the actual value is " + secondArgument, throwable);
                } else {
                    StreamObserver<?> streamObserver = (StreamObserver) secondArgument;
                    //TODO translate exception
                    streamObserver.onError(exceptionTranslator(throwable).asException());
                }
            }
        }
    }

    private Status exceptionTranslator(Throwable ex) {
        return Status.INTERNAL.withCause(ex).withDescription(ex.getMessage());
    }
}
