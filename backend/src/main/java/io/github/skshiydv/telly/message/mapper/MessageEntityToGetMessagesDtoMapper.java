package io.github.skshiydv.telly.message.mapper;

import io.github.skshiydv.telly.message.entity.MessageEntity;
import io.github.skshiydv.telly.message.model.GetMessages;

import java.util.function.Function;

public class MessageEntityToGetMessagesDtoMapper implements Function<MessageEntity, GetMessages> {
    public static  final MessageEntityToGetMessagesDtoMapper Mapper = new MessageEntityToGetMessagesDtoMapper();
    private MessageEntityToGetMessagesDtoMapper() {}
    @Override
    public GetMessages apply(MessageEntity messageEntity) {
        GetMessages msg = new GetMessages();
        msg.setMessage(messageEntity.getMessageContent());
        msg.setReceiver(messageEntity.getReceiver());
        msg.setSender(messageEntity.getSender());
        return msg;
    }
}
