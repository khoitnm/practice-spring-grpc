package org.tnmk.practice.springgrpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.client.samplestory.streamdownloadzipfilesclient.StreamDownloadZipFilesClient;

@Service
public class Initiation {
    @Autowired
    private StreamDownloadZipFilesClient streamDownloadZipFilesClient;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        streamDownloadZipFilesClient.downloadFileFromServer();
    }
}
