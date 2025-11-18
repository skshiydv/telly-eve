package io.github.skshiydv.telly.message.service;

import io.github.skshiydv.telly.message.model.GetMessages;
import io.github.skshiydv.telly.message.model.MessageRq;

import java.util.List;


public interface MessageService {
    List<GetMessages> getMessages(String roomName);
    GetMessages sendMessage(String roomName, MessageRq message);

}
