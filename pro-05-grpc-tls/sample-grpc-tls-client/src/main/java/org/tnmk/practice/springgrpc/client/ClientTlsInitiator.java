package org.tnmk.practice.springgrpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpctlsclient.Item;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpctlsclient.ItemSampleGrpcTlsClient;

@Service
public class ClientTlsInitiator {

    private final ItemSampleGrpcTlsClient itemSampleGrpcTlsClient;

    @Autowired
    public ClientTlsInitiator(ItemSampleGrpcTlsClient itemSampleGrpcTlsClient) {
        this.itemSampleGrpcTlsClient = itemSampleGrpcTlsClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Item item = itemSampleGrpcTlsClient.getItem(""+System.nanoTime());
    }
}
