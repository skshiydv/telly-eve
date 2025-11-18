package io.github.skshiydv.telly.room.service;

import io.github.skshiydv.telly.message.model.GetMessages;
import io.github.skshiydv.telly.room.model.CreateRoomDTO;
import io.github.skshiydv.telly.room.model.GetRoomDto;

import java.util.List;

public interface RoomService {
     String createRoom(CreateRoomDTO createRoomDTO);
     List<GetMessages> getAllMessages(Long roomId);
    GetRoomDto getRoomByRoomName(String roomName);
    String addUser(String roomName);
    List<GetRoomDto> getAllRooms();
    List<GetRoomDto> getAllRoomsByUserId();
    List<GetRoomDto> getAllUnjoinedRooms();
    String leaveRoom(String roomName);

}
