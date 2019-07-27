package org.tnmk.practice.springgrpc.samplegrpcserver02errorandheader.samplestory.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tnmk.practice.springgrpc.protobuf.ItemProto;

import java.util.Date;
import java.util.Optional;

@Service
public class SampleItemService {
    public Optional<ItemProto> getItem(String id) {
        if (!StringUtils.isEmpty(id) && id.matches("[0-9]*")) {
            if (id.equals("0")) {
                return Optional.empty();
            } else {
                ItemProto itemProto = ItemProto.newBuilder()
                    .setId(id)
                    .setName("Some name " + new Date()).build();
                return Optional.of(itemProto);
            }
        } else {
            throw new IllegalArgumentException("The id must not be empty and must be a number.");
        }
    }
}
