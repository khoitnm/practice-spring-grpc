package org.tnmk.practice.springgrpc.downloadfileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DownloadFileServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DownloadFileServerApplication.class, args);
  }
}
