Incompatible version of:
<io.grpc-version>1.15.1</io.grpc-version>
<io.netty-version>2.0.12.Final</io.netty-version>

```
***************************
APPLICATION FAILED TO START
***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    io.netty.handler.ssl.OpenSsl.<clinit>(OpenSsl.java:203)

The following method did not exist:

    io.netty.internal.tcnative.SSLContext.setCipherSuite(JLjava/lang/String;Z)Z

The method's class, io.netty.internal.tcnative.SSLContext, is available from the following locations:

    jar:file:/home/kevintran/.m2/repository/io/netty/netty-tcnative-boringssl-static/2.0.12.Final/netty-tcnative-boringssl-static-2.0.12.Final.jar!/io/netty/internal/tcnative/SSLContext.class

It was loaded from the following location:

    file:/home/kevintran/.m2/repository/io/netty/netty-tcnative-boringssl-static/2.0.12.Final/netty-tcnative-boringssl-static-2.0.12.Final.jar


Action:

Correct the classpath of your application so that it contains a single, compatible version of io.netty.internal.tcnative.SSLContext
```