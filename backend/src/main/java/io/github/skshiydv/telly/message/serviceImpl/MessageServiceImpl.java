package io.github.skshiydv.telly.message.serviceImpl;

import io.github.skshiydv.telly.message.entity.MessageEntity;
import io.github.skshiydv.telly.message.mapper.MessageEntityToGetMessagesDtoMapper;
import io.github.skshiydv.telly.message.mapper.MessageRqtoMessageEntityMapper;
import io.github.skshiydv.telly.message.model.GetMessages;
import io.github.skshiydv.telly.message.model.MessageRq;
import io.github.skshiydv.telly.message.repository.MessageRepository;
import io.github.skshiydv.telly.message.service.MessageService;
import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.room.repository.RoomRepository;
import io.github.skshiydv.telly.room.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final RoomService roomService;

    public MessageServiceImpl(RoomRepository roomRepository, MessageRepository messageRepository, RoomService roomService) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.roomService = roomService;
    }

    @Override
    public List<GetMessages> getMessages(String roomName) {

        List<GetMessages> getMessages = null;
        try {
            RoomEntity room = roomRepository.findByRoomName(roomName);
            List<MessageEntity> messageEntities = room.getMessages();
            getMessages = new ArrayList<>();
            for (MessageEntity messageEntity : messageEntities) {
                GetMessages getMessage = new GetMessages();
                getMessage = MessageEntityToGetMessagesDtoMapper.Mapper.apply(messageEntity);
                getMessages.add(getMessage);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getMessages;
    }

    @Transactional
    @Override
    public GetMessages sendMessage(String roomName, MessageRq message) {
        ArrayList<GetMessages> msgs = new ArrayList<>();
        GetMessages getMessage = new GetMessages();
        try {
            RoomEntity room = roomRepository.findByRoomName(roomName);
            MessageEntity msgEntity = new MessageEntity();
            msgEntity = MessageRqtoMessageEntityMapper.INSTANCE.apply(message);
            room.getMessages().add(msgEntity);
            messageRepository.save(msgEntity);
            roomRepository.save(room);

            getMessage.setMessage(message.getMessageContent());
            getMessage.setSender(message.getSender());
            getMessage.setReceiver(message.getReceiver());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return getMessage;
    }
}
