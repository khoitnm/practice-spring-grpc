package org.tnmk.practice.springgrpc.pro04serverinterceptormultithread.samplestory.service;

import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

@Service
public class SampleItemService {
    public ItemProto getItem(String correlationId, String id) {
        ItemProto itemProto = ItemProto.newBuilder()
            .setId(id)
            .setDescription(correlationId)
            .setName("SampleName" + id).build();
        return itemProto;
    }
}
