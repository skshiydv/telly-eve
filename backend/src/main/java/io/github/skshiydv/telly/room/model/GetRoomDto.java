package io.github.skshiydv.telly.room.model;

import io.github.skshiydv.telly.message.entity.MessageEntity;
import io.github.skshiydv.telly.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRoomDto {
    private String roomName;
    private String roomDescription;
    private List<MessageEntity> messages;
    List<UserEntity> users;

}
