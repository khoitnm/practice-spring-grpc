> If you find there are something which could be improved, please create a Merge Request. 
> Thank you so much!

# I. Introduction

You can import the whole project with all modules into your IDE, or you can import some modules separately.

1.  `common-grpc`: The common code related to gRPC which can be reused in other projects. At this moment, it just contains the common dependencies for other grpc server modules (such as `pro01-simple-grpc` & `pro02-error-handler`). 

2. `sample-service-proto`: Declare the grpc servers in *.proto file.
    
3. `grpc-client`: The client application which will connect to grpc servers.

4. `pro01-simple-grpc`: Provides some simple gRPC endpoints

5. `pro02-error-handler`: Provides a central error handler for gRPC endpoints by using AOP. 
We also have an Component Test which will start the grpcServer, then the client stub will connect to that server in the test cases. 

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
The source code of gRPC servers have prefix `proXX` such as `pro01-simple-grpc` or `pro02-error-handler`, so you can start any one of those servers.

1. You can  by using your IDE, and start the file `Application.java` in the corresponding server module.

2. Or you can use the command line:

Access the folder which contains source code of server:
```
cd pro01-simple-grpc
```

Start application inside that module:
```
mvn spring-boot:run 
``` 
Or you can use:
```
java -jar target/pro01-simple-grpc-0.0.1-SNAPSHOT.jar 
```

To stop it, press `Ctrl-C`

### Start the client
Similar to the server, you now can start a client
```
cd grpc-client
mvn spring-boot:run
```
After starting successfully, you can open your browser (Chrome) and send a REST request to ```http://localhost:8080/items/1```
It will send http request to the grpc-client, then grpc-client will send a gRPC request to our gRPC server (so you have to start your gRPC server first). 

To test the error case:
```http://localhost:8080/items/any-string-which-is-not-a-number```

To test the returning an empty object case:
```http://localhost:8080/items/0```

# References
+ https://github.com/LogNet/grpc-spring-boot-starter