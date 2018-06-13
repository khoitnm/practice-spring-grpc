package org.tnmk.practice.springgrpc.pro02errorhandler;

import io.grpc.Server;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.grpc.testing.GrpcServerRule;
import org.tnmk.practice.springgrpc.pro02errorhandler.resource.ItemProtoResource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemProtoResourceTest {
    /**
     * This rule manages automatic graceful shutdown for the registered servers and channels at the
     * end of test.
     */
    @Rule
    public final GrpcServerRule grpcCleanup = new GrpcServerRule();

    @Autowired
    private ItemProtoResource itemProtoResource;

    public void test() {
//        String serverName = InProcessServerBuilder.generateName();
//        Server server = InProcessServerBuilder.forName(serverName).directExecutor().addService(itemProtoResource).build();
//        GrpcServerRule grpcCleanup = new GrpcServerRule(server);

        grpcCleanup.getServiceRegistry().addService(itemProtoResource);

        
//        grpcCleanup.register();

    }
}
