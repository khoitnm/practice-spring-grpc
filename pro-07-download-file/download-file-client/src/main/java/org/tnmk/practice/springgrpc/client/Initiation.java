package org.tnmk.practice.springgrpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.client.samplestory.downloadfileclient.DownloadFileClient;

@Service
public class Initiation {
    @Autowired
    private DownloadFileClient downloadFileClient;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        downloadFileClient.downloadFileFromServer();
    }
}
