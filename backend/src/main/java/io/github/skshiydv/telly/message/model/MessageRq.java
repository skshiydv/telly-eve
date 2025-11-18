package io.github.skshiydv.telly.message.model;

import lombok.Data;

@Data
public class MessageRq {
    private String sender;
    private String receiver;
    private String messageContent;
}
