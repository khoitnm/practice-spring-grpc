You can import the whole project with all modules into your IDE, or you can import some modules separately.

1.  `common-grpc`: The common code related to gRPC which can be reused in other projects.

2. `grpc-resource`: Declare the grpc servers in *.proto file.
    
3. `grpc-client`: The client application which will connect to grpc servers.

4. `pro01-simple-grpc`: Provides some simple gRPC endpoints

5. `pro02-error-handler`: Provides a central error handler for gRPC endpoints by using AOP. 
We also have an Component Test which will start the grpcServer, then the client stub will connect to that server in the test cases. 
