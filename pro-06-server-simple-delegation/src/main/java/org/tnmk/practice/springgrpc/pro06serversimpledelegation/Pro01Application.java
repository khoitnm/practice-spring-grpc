package org.tnmk.practice.springgrpc.pro06serversimpledelegation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Pro01Application {

  public static void main(String[] args) {
    SpringApplication.run(Pro01Application.class, args);
  }
}
