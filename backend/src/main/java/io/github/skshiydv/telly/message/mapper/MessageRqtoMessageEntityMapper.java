package io.github.skshiydv.telly.message.mapper;

import io.github.skshiydv.telly.message.entity.MessageEntity;
import io.github.skshiydv.telly.message.model.MessageRq;

import java.time.LocalDateTime;
import java.util.function.Function;

public class MessageRqtoMessageEntityMapper implements Function<MessageRq, MessageEntity> {
    public static final MessageRqtoMessageEntityMapper INSTANCE = new MessageRqtoMessageEntityMapper();

    private MessageRqtoMessageEntityMapper() {
    }

    @Override
    public MessageEntity apply(MessageRq messageRq) {
        MessageEntity msg = new MessageEntity();
        msg.setSender(messageRq.getSender());
        msg.setReceiver(messageRq.getReceiver());
        msg.setMessageContent(String.valueOf(messageRq.getMessageContent()));
        msg.setTimestamp(LocalDateTime.now());
        return msg;
    }
}
