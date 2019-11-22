> If you find there are something which could be improved, please create a Merge Request. 
> Thank you so much!

# I. Introduction

You can import the whole project with all modules into your IDE, or you can import some modules separately.

1.  `common-grpc`: The common code related to gRPC which can be reused in other projects. At this moment, it just contains the common dependencies for other grpc server modules (such as `sample-grpc-server-01-simple` & `sample-grpc-server-02-error-and-header`). 

2. `sample-proto`: Declare the grpc servers and messages in *.proto file. This proto will be used for most of the sample client & server applications such as `pro-01-client-simple`, `pro-01-server-simple`, `pro-03-server-interceptor`, etc.
    
3. `pro-01-client-simple`: The client application which will connect to grpc servers.

4. `pro-01-server-simple`: Provides some simple gRPC endpoints

5. `pro-02-server-simple-header-and-error`: Provides a central error handler for gRPC endpoints by using AOP. 
We also have an Component Test which will start the grpcServer, then the client stub will connect to that server in the test cases.

etc.

# II. Build projects
Run the command line, it will complie the source code, build project, and then run tests.
```
mvn clean install 
```

If you want to build without testing, run the command line:
```
mvn clean install -DskipTests 
```

# III. Start Application.

### Start a gRPC server
The source code of gRPC servers have prefix `proXX` such as `sample-grpc-server-01-simple` or `sample-grpc-server-02-error-and-header`, so you can start any one of those servers.

1. You can  by using your IDE, and start the file `Application.java` in the corresponding server module.

2. Or you can use the command line:

Access the folder which contains source code of server:
```
cd sample-grpc-server-01-simple
```

Start application inside that module:
```
mvn spring-boot:run 
``` 
Or you can use:
```
java -jar target/sample-grpc-server-01-simple-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`

### Start the client
Similar to the server, you now can start a client
```
cd pro-01-client-simple
mvn spring-boot:run
```
After starting successfully, you can open your browser (Chrome) and send a REST request to ```http://localhost:8080/items/1```
It will send http request to the pro-01-client-simple, then pro-01-client-simple will send a gRPC request to our gRPC server (so you have to start your gRPC server first). 

To test the error case:
```http://localhost:8081/items/any-string-which-is-not-a-number```

To test the returning a null object case:
```http://localhost:8081/items/null```

To test the returning an empty object case:
```http://localhost:8081/items/0```

# References
+ https://github.com/LogNet/grpc-spring-boot-starter
+ https://thenewstack.io/build-real-world-microservices-with-grpc/
+ Upload file:
    - https://github.com/grpc/grpc-go/issues/414 
    - https://github.com/tzutalin/example-grpc/tree/master/java/src/main/java

# TLS gRPC 
Note: To make TLS gRPC works, the versions of dependencies must be compatible. Please look at the table in this link:
https://github.com/grpc/grpc-java/blob/master/SECURITY.md 
However, when running, here's what happenings:
Case 1: Works
    io-grpc.version: 1.11.0
    io-netty.version: 2.0.25.Final

Case 2: Doesn't Work
    io-grpc.version: 1.11.0
    io-netty.version: 2.0.7.Final (follow the compatible versions table)    
    
# Trouble shooting
grpc-netty 1.11.0 has a bug which consume tons of CPU when there's no requests send to the server after a few hours.
To solve that problem, we need to use the newer netty version.
However, that netty is coupled inside spring boot and grpc-netty, that's why we need to upgrade versions for both of them.     