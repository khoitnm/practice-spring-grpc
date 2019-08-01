package org.tnmk.practice.springgrpc.samplegrpcserver01simple.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;

@Service
public class SampleItemService {
    public ItemProto getItem(String id) throws ItemNotFoundException {
        if ("null".equalsIgnoreCase(id)) {
            return null;
        } else if (StringUtils.isEmpty(id) || !id.matches("[0-9]*")) {
            throw new IllegalItemIdException("Item Id must be not null and contains only number digit", id);
        } else if (id.equals(ItemIdConstants.EMPTY_ITEM_ID)) {
            return ItemProto.newBuilder().build();
        } else if (id.equals(ItemIdConstants.NOT_FOUND_ITEM_ID)) {
            throw new ItemNotFoundException("Not found item with id 0", id);
        }

        ItemProto itemProto = ItemProto.newBuilder()
            .setId(id)
            .setName("Some name " + new Date()).build();
        return itemProto;
    }
}
