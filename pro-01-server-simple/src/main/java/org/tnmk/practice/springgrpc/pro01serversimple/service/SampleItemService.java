package org.tnmk.practice.springgrpc.pro01serversimple.service;

import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;

@Service
public class SampleItemService {
    public ItemProto getItem(String id) {
        ItemProto itemProto = ItemProto.newBuilder()
            .setId(id)
            .setName("Some name " + new Date()).build();
        return itemProto;
    }
}
