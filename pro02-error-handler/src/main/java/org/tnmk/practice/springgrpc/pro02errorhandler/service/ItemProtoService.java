package org.tnmk.practice.springgrpc.pro02errorhandler.service;

import io.grpc.Status;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;

@Service
public class ItemProtoService {
    public ItemProto getItem(String id){
        if (!StringUtils.isEmpty(id) && id.matches("[0-9]*")) {
            ItemProto itemProto = ItemProto.newBuilder()
                .setId(id)
                .setName("Some name " + new Date()).build();
            return itemProto;
        } else {
            throw new IllegalArgumentException("The id must not be empty and must be a number.");
        }
    }
}
