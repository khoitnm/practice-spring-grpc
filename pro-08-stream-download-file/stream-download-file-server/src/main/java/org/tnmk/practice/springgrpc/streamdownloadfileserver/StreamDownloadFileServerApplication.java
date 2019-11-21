package org.tnmk.practice.springgrpc.streamdownloadfileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StreamDownloadFileServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamDownloadFileServerApplication.class, args);
  }
}
