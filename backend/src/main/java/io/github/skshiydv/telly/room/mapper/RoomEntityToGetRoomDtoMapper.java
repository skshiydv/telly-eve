package io.github.skshiydv.telly.room.mapper;

import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.room.model.GetRoomDto;

import java.util.function.Function;

public class RoomEntityToGetRoomDtoMapper implements Function<RoomEntity, GetRoomDto> {
    public static final RoomEntityToGetRoomDtoMapper INSTANCE = new RoomEntityToGetRoomDtoMapper();
    private RoomEntityToGetRoomDtoMapper() {}
    @Override
    public GetRoomDto apply(RoomEntity roomEntity) {
        GetRoomDto getRoomDto = new GetRoomDto();
        getRoomDto.setRoomName(roomEntity.getRoomName());
        getRoomDto.setRoomDescription(roomEntity.getRoomDescription());
        getRoomDto.setMessages(roomEntity.getMessages());
        getRoomDto.setUsers(roomEntity.getUsers());
        return getRoomDto;
    }
}
