package org.tnmk.skeleton.springgrpc.client.grpcclient;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

@Component
public class ProtoMapper {

    public Item itemProtoToItem(ItemProto itemProto) {
        Item item = new Item();
        BeanUtils.copyProperties(itemProto, item);
        return item;
    }

}
