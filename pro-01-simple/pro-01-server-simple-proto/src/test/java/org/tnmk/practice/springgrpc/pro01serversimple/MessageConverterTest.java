package org.tnmk.practice.springgrpc.pro01serversimple;


import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tnmk.practice.springgrpc.protobuf.MessagAChild;
import org.tnmk.practice.springgrpc.protobuf.MessageA;
import org.tnmk.practice.springgrpc.protobuf.MessageB;

public class MessageConverterTest {

    @Test
    public void testDataCanBeTransferredFromDifferentMessageTypesBecauseTheyHaveTheSameFieldOrder() {

        MessageA messageA = MessageA.newBuilder()
                .setActiveA(true)
                .setIdLong(System.nanoTime())
                .setName("MessageAName" + System.nanoTime())
                .setChildA(MessagAChild.newBuilder().setName("ChildA"+System.nanoTime()).build())
                .build();


        byte[] bytes = messageA.toByteArray();
        MessageB messageB = parseMessageBFromBytes(bytes);

        //Check whether data from MessageA should be able to converted to MessageB because the have the same field order.
        Assertions.assertEquals(messageA.getActiveA(), messageB.getActiveB());
        Assertions.assertEquals(messageA.getName(), messageB.getName());
        Assertions.assertEquals((int)messageA.getIdLong(), messageB.getIdInt());
        Assertions.assertEquals(messageA.getChildA().getName(), messageB.getChildB().getName());
    }

    private MessageB parseMessageBFromBytes(byte[] bytes) {
        try {
            MessageB messageB = MessageB.parseFrom(bytes);
            return messageB;
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Cannot convert bytes to MessageB: " + e.getMessage(), e);
        }
    }
}
