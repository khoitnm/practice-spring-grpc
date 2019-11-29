package org.tnmk.practice.springgrpc.pro01serversimple.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.lang.invoke.MethodHandles;
import java.util.Date;

@Service
public class SampleItemService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ItemProto getItem(String id) {
        logger.info("GetItem - request: " + id);
        ItemProto itemProto = ItemProto.newBuilder()
                .setId(id)
                .setName("Some name " + new Date())
                .setDescription("Some description " + System.nanoTime())
                .build();
        logger.info("GetItem - result: \n" + itemProto);
        return itemProto;
    }
}
