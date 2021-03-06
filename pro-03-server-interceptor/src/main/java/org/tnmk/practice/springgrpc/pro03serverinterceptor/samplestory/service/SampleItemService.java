package org.tnmk.practice.springgrpc.pro03serverinterceptor.samplestory.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;
import java.util.Optional;

@Service
public class SampleItemService {
    public ItemProto getItem(String id) {
        ItemProto itemProto = ItemProto.newBuilder()
            .setId(id)
            .setName("Some name " + new Date()).build();
        return itemProto;
    }
}
