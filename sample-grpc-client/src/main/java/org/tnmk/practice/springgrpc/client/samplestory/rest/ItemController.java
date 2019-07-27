package org.tnmk.practice.springgrpc.client.samplestory.rest;

import java.util.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.common.grpc.client.MDCConstants;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpcclient.Item;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpcclient.ItemId;
import org.tnmk.practice.springgrpc.client.samplestory.samplegrpcclient.ItemSampleGrpcClient;

@RestController
public class ItemController {
    private final ItemSampleGrpcClient itemProtoClient;

    @Autowired
    public ItemController(ItemSampleGrpcClient itemProtoClient) {
        this.itemProtoClient = itemProtoClient;
    }

    @RequestMapping("/items/{id}")
    public Item getItems(@PathVariable("id") String id) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put(MDCConstants.CORRELATION_ID, correlationId);
        ItemId itemId = new ItemId();
        itemId.setId(id);
        Item item = itemProtoClient.getItem(itemId);
        return item;
    }

}