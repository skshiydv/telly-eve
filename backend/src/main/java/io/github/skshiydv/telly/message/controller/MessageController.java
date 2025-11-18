package io.github.skshiydv.telly.message.controller;

import io.github.skshiydv.telly.message.model.GetMessages;
import io.github.skshiydv.telly.message.model.MessageRq;
import io.github.skshiydv.telly.message.repository.MessageRepository;
import io.github.skshiydv.telly.message.service.MessageService;
import io.github.skshiydv.telly.room.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;

    public MessageController(RoomRepository roomRepository, MessageRepository messageRepository, MessageService messageService) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }
    @GetMapping("/get/{roomName}")
    public ResponseEntity<List<GetMessages>> getMessages(@PathVariable String roomName) {
        return ResponseEntity.ok(messageService.getMessages(roomName));
    }
    @MessageMapping("/sendMessage/{roomName}")
    @SendTo("/topic/room/{roomName}")
    public ResponseEntity<GetMessages> sendMessage(@DestinationVariable String roomName, @RequestBody MessageRq message) {
        return new ResponseEntity<>(messageService.sendMessage(roomName, message), HttpStatus.OK);
    }
}
