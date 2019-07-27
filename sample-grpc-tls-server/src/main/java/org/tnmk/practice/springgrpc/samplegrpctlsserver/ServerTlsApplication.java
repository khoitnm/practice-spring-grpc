package org.tnmk.practice.springgrpc.samplegrpctlsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServerTlsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerTlsApplication.class, args);
  }
}
