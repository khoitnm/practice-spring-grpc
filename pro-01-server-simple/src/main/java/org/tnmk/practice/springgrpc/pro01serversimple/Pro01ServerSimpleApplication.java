package org.tnmk.practice.springgrpc.pro01serversimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Pro01ServerSimpleApplication {

  public static void main(String[] args) {
    SpringApplication.run(Pro01ServerSimpleApplication.class, args);
  }
}
