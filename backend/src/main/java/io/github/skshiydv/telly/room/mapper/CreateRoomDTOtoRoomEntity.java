package io.github.skshiydv.telly.room.mapper;

import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.room.model.CreateRoomDTO;

import java.util.function.Function;

public class CreateRoomDTOtoRoomEntity implements Function<CreateRoomDTO, RoomEntity> {
    public static final CreateRoomDTOtoRoomEntity INSTANCE = new CreateRoomDTOtoRoomEntity();
   private CreateRoomDTOtoRoomEntity() {}
    @Override
    public RoomEntity apply(CreateRoomDTO createRoomDTO) {
        RoomEntity roomEntity = new RoomEntity();
        if (createRoomDTO!=null){
        roomEntity.setRoomDescription(createRoomDTO.getRoomDescription());
        roomEntity.setRoomName(createRoomDTO.getRoomName());
        roomEntity.setRoomType(createRoomDTO.getRoomType());
       }
        return roomEntity;
    }
}
