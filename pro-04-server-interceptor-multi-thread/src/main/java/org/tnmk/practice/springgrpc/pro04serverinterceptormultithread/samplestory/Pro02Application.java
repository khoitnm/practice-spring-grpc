package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Pro02Application {

  public static void main(String[] args) {
    SpringApplication.run(Pro02Application.class, args);
  }
}
