package org.tnmk.practice.springgrpc.downloadfileserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;

@Service
public class SampleItemService {
    public static final Logger logger = LoggerFactory.getLogger(SampleItemService.class);

    public ItemProto getItem(String id) throws ItemNotFoundException {
        logger.info("Start getting item " + id);
        if ("null".equalsIgnoreCase(id)) {
            return null;
        } else if (StringUtils.isEmpty(id) || !id.matches("[0-9]*")) {
            throw new IllegalItemIdException("Item Id must be not null and contains only number digit", id);
        } else if (id.equals("0")) {
            throw new ItemNotFoundException("Not found item with id 0", id);
        }

        ItemProto itemProto = ItemProto.newBuilder()
            .setId(id)
            .setName("Some name " + new Date()).build();
        return itemProto;
    }
}
