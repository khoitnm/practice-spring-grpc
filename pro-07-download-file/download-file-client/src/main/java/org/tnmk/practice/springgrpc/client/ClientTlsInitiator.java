package org.tnmk.practice.springgrpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.client.samplestory.downloadfileclient.Item;
import org.tnmk.practice.springgrpc.client.samplestory.downloadfileclient.ItemDownloadFileClient;

@Service
public class ClientTlsInitiator {

    private final ItemDownloadFileClient itemDownloadFileClient;

    @Autowired
    public ClientTlsInitiator(ItemDownloadFileClient itemDownloadFileClient) {
        this.itemDownloadFileClient = itemDownloadFileClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Item item = itemDownloadFileClient.getItem(""+System.nanoTime());
    }
}
