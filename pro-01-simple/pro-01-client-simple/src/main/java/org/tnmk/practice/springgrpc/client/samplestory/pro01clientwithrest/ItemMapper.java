package org.tnmk.practice.springgrpc.client.samplestory.pro01clientwithrest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

/**
 * The destination object could be constructed from many different sources.
 * So we name the mapper/builder/resolver following the target object, not the source objects.
 */
public class ItemMapper {

    public static Item toItem(ItemProto itemProto) {
        Item item = new Item();
        item.setId(itemProto.getId());
        item.setDescription(itemProto.getDescription());
        item.setName(itemProto.getName());
        //BeanUtils.copyProperties(itemProto, item); //You can replace setting fields statements by this copyProperties().
        return item;
    }

}
