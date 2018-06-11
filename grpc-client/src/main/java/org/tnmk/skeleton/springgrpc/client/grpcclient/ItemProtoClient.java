package org.tnmk.skeleton.springgrpc.client.grpcclient;

public interface ItemProtoClient {
    Item getItem(ItemId itemId);
}
