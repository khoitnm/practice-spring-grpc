package org.tnmk.practice.springgrpc.pro06serversimpledelegation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.practice.springgrpc.pro06serversimpledelegation.externalsystem.ExternalGrpcSystemService;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;

@Service
public class SampleItemService {
    public static final String STOP_CHAR = "-";
    private final ExternalGrpcSystemService externalGrpcSystemService;

    @Autowired
    public SampleItemService(ExternalGrpcSystemService externalGrpcSystemService) {
        this.externalGrpcSystemService = externalGrpcSystemService;
    }

    public ItemProto getItem(String id) {
        ItemProto itemProto;
        if (id.contains(STOP_CHAR)) {
            itemProto = ItemProto.newBuilder()
                .setId(id)
                .setName("Some name " + new Date()).build();
            return itemProto;
        } else {
            id = STOP_CHAR + id;
            itemProto = externalGrpcSystemService.getExternalItem(id);
        }
        return itemProto;
    }
}
