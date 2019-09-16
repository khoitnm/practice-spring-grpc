package com.leonardo.monalisa


import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.tnmk.practice.springgrpc.grpcclientapp.GrpcClientApplication
import spock.lang.Specification

@Ignore
@DirtiesContext
//@ActiveProfiles("dev")
//@ActiveProfiles("prdgenerateddns")
//@ActiveProfiles("prdexternaldns")
@ActiveProfiles("portforward")
@ContextConfiguration(initializers = [])
@SpringBootTest(classes = GrpcClientApplication.class)
abstract class BaseSpecification extends Specification {

}