package org.tnmk.skeleton.springgrpc.client.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.skeleton.springgrpc.client.grpcclient.Item;
import org.tnmk.skeleton.springgrpc.client.grpcclient.ItemId;
import org.tnmk.skeleton.springgrpc.client.grpcclient.ItemProtoClient;

@RestController
public class ItemController {
    private final ItemProtoClient itemProtoClient;

    @Autowired
    public ItemController(ItemProtoClient itemProtoClient) {
        this.itemProtoClient = itemProtoClient;
    }

    @RequestMapping("/items/{id}")
    public Item getItems(@PathVariable("id") String id) {
        ItemId itemId = new ItemId(); itemId.setId(id);
        Item item = itemProtoClient.getItem(itemId);
        return item;
    }

}