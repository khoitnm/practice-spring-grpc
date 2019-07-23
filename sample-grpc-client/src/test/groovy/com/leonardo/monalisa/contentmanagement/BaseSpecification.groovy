package com.leonardo.monalisa.contentmanagement


import org.junit.Ignore
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.tnmk.practice.springgrpc.client.ClientApplication
import spock.lang.Specification

@Ignore
@DirtiesContext
@ActiveProfiles("componenttest")
@ContextConfiguration(initializers = [])
@SpringBootTest(classes = ClientApplication.class)
abstract class BaseSpecification extends Specification {



}