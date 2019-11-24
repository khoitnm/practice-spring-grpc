package org.tnmk.practice.springgrpc.streamdownloadzipfilesserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync //For ZipDataStreaming
public class StreamDownloadZipFilesServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamDownloadZipFilesServerApplication.class, args);
  }
}
