package org.tnmk.practice.springgrpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.client.samplestory.pro01clientwithrest.Item;
import org.tnmk.practice.springgrpc.client.samplestory.pro01clientwithrest.ItemPro01Client;

@Service
public class Initiation {
    public static final Logger logger = LoggerFactory.getLogger(Initiation.class);

    private final ItemPro01Client itemPro01Client;

    @Autowired
    public Initiation(ItemPro01Client itemPro01Client) {
        this.itemPro01Client = itemPro01Client;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Item item = itemPro01Client.getItem("RandomItemId" + System.nanoTime());
        logger.info("Receive item from server: {}", item);
    }
}
