package com.leonardo.monalisa.monalisa

import com.leonardo.monalisa.BaseSpecification
import com.leonardo.monalisa.contentmanagement.content.proto.ContentProto
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.ContentVscapeSyndicationGrpcServiceGrpc
import com.leonardo.monalisa.contentmanagement.contentsyndication.proto.HotelViewIdProto
import com.leonardo.monalisa.usermanagement.user.proto.AccountUserListProto
import com.leonardo.monalisa.usermanagement.user.proto.AccountUsersRequestProto
import com.leonardo.monalisa.usermanagement.user.proto.UserGrpcServiceGrpc
import com.leonardo.monalisa.usermanagement.userauthentication.proto.UserAuthenticationGrpcServiceGrpc
import com.leonardo.monalisa.usermanagement.userauthentication.proto.UserAuthenticationRequestProto
import com.leonardo.monalisa.usermanagement.userauthentication.proto.UserAuthenticationResponseProto
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.tnmk.practice.springgrpc.grpcclientapp.common.MDCConstants
import org.tnmk.practice.springgrpc.grpcclientapp.config.GrpcClientStubFactory

class UserGrpcServiceSpec extends BaseSpecification {

    @Autowired
    private GrpcClientStubFactory grpcClientStubFactory;

    private UserGrpcServiceGrpc.UserGrpcServiceBlockingStub stub;

    private UserAuthenticationGrpcServiceGrpc.UserAuthenticationGrpcServiceBlockingStub authenticationGrpcServiceBlockingStub;
    /**
     * run before the first feature method
     * @return
     */
    def setup() {
        authenticationGrpcServiceBlockingStub = grpcClientStubFactory.constructStub(
                "user-management",
                UserAuthenticationGrpcServiceGrpc.UserAuthenticationGrpcServiceBlockingStub.class);

        stub = grpcClientStubFactory.constructStub(
                "user-management",
                UserGrpcServiceGrpc.UserGrpcServiceBlockingStub.class);
    }

//    @Ignore
    def 'Find users inside an Account'() {
        given:
        String oidAccount = "91496704-821d-11e9-97da-270517923c8b";

        when:
        UserAuthenticationRequestProto authenticationRequestProto = UserAuthenticationRequestProto.newBuilder()
            .setOidAccount(oidAccount)
            .setEmail("owner@langham.com")
            .setPassword("so safe")
            .build();
        UserAuthenticationResponseProto userAuthenticationResponseProto = authenticationGrpcServiceBlockingStub.authenticateUser(authenticationRequestProto);
        String jwt = userAuthenticationResponseProto.jwt;

        MDC.put(MDCConstants.JWT, jwt);

        AccountUsersRequestProto accountUsersRequestProto = AccountUsersRequestProto.newBuilder()
            .setOidAccount(oidAccount)
            .build();

        AccountUserListProto accountUserListProto = stub.getAccountUsers(accountUsersRequestProto)
        System.out.println(accountUserListProto);

        then:
        1 == 1

    }

}
