package org.tnmk.skeleton.springgrpc.client.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tnmk.practice.springgrpc.protobuf.ItemDTO;
import org.tnmk.practice.springgrpc.protobuf.ItemIdDTO;
import org.tnmk.skeleton.springgrpc.client.grpcclient.ItemGrpcClient;

@RestController
public class ItemController {
    private final ItemGrpcClient itemGrpcClient;

    @Autowired
    public ItemController(ItemGrpcClient itemGrpcClient) {
        this.itemGrpcClient = itemGrpcClient;
    }

    @RequestMapping("/items/{id}")
    public ItemDTO getItems(@PathVariable("id") String id) {
        ItemIdDTO itemIdDTO = ItemIdDTO.newBuilder().setId(id).build();
        ItemDTO itemDTO = itemGrpcClient.getItem(itemIdDTO);
        return itemDTO;
    }

}