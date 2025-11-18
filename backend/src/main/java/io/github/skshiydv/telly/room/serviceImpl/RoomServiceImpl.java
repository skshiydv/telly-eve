package io.github.skshiydv.telly.room.serviceImpl;

import io.github.skshiydv.telly.message.mapper.MessageEntityToGetMessagesDtoMapper;
import io.github.skshiydv.telly.message.model.GetMessages;
import io.github.skshiydv.telly.room.entity.RoomEntity;
import io.github.skshiydv.telly.room.mapper.CreateRoomDTOtoRoomEntity;
import io.github.skshiydv.telly.room.mapper.RoomEntityToGetRoomDtoMapper;
import io.github.skshiydv.telly.room.model.CreateRoomDTO;
import io.github.skshiydv.telly.room.model.GetRoomDto;
import io.github.skshiydv.telly.room.repository.RoomRepository;
import io.github.skshiydv.telly.room.service.RoomService;
import io.github.skshiydv.telly.user.entity.UserEntity;
import io.github.skshiydv.telly.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createRoom(CreateRoomDTO createRoomDTO) {
        if (roomRepository.findByRoomName(createRoomDTO.getRoomName()) != null) return "Room already exists";
        if (createRoomDTO.getRoomName() == null || createRoomDTO.getRoomName().isEmpty()) {
            return "Room name is empty";
        }
        if (createRoomDTO.getRoomType() == null || createRoomDTO.getRoomType().isEmpty()) {
            return "Room Type is empty";
        }
        if (createRoomDTO.getRoomDescription() == null || createRoomDTO.getRoomDescription().isEmpty()) {
            return "Room description is empty";
        }
        RoomEntity entity = new RoomEntity();
        entity = CreateRoomDTOtoRoomEntity.INSTANCE.apply(createRoomDTO);
        roomRepository.save(entity);
        return "ok";
    }

    @Override
    public List<GetMessages> getAllMessages(Long roomId) {
        if (!roomRepository.findById(roomId).isPresent()) return null;
        RoomEntity roomEntity = roomRepository.findById(roomId).get();
        if (roomEntity.getMessages() == null) return new ArrayList<>();
        List<GetMessages> getMessages = new ArrayList<>();
        roomEntity.getMessages().forEach(message -> {
            getMessages.add(MessageEntityToGetMessagesDtoMapper.Mapper.apply(message));
        });
        return getMessages;
    }

    @Override
    public GetRoomDto getRoomByRoomName(String roomName) {
        if (roomName == null || roomName.isEmpty()) return null;
        if (roomRepository.findByRoomName(roomName) == null) return null;
        GetRoomDto dto = RoomEntityToGetRoomDtoMapper.INSTANCE.apply(roomRepository.findByRoomName(roomName));
        return dto;
    }

    @Override
    public String addUser(String roomName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            OAuth2User user = (OAuth2User) auth.getPrincipal();
            String email = user.getAttribute("email");

            UserEntity currUser = userRepository.findByEmail(email);
            RoomEntity room = roomRepository.findByRoomName(roomName);

            if (currUser == null || room == null || room.getId() == null) {
                return "User or room not found / not persisted";
            }

            // Avoid duplicates
            if (!currUser.getRooms().contains(room)) {
                currUser.getRooms().add(room);
                userRepository.save(currUser);
            }
            room.getUsers().add(currUser);
            roomRepository.save(room);

            return "User successfully added to room";

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public List<GetRoomDto> getAllRooms() {
        List<RoomEntity> roomEntities = roomRepository.findAll();
        List<GetRoomDto> dtos = new ArrayList<>();
        roomEntities.stream().forEach(room -> {
            GetRoomDto dto = RoomEntityToGetRoomDtoMapper.INSTANCE.apply(room);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<GetRoomDto> getAllRoomsByUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        String email = user.getAttribute("email");
        UserEntity currUser = userRepository.findByEmail(email);
        if (currUser == null) return null;
        List<RoomEntity> allRooms = roomRepository.findByUsers(currUser);
        List<GetRoomDto> dtos = new ArrayList<>();
        allRooms.stream().forEach(room -> {
            GetRoomDto dto = RoomEntityToGetRoomDtoMapper.INSTANCE.apply(room);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public List<GetRoomDto> getAllUnjoinedRooms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        String email = user.getAttribute("email");
        UserEntity currUser = userRepository.findByEmail(email);
        List<RoomEntity> rooms = roomRepository.findAll();
        List<GetRoomDto> dtos = new ArrayList<>();
        rooms.forEach(room -> {
            if(!room.getUsers().contains(currUser)) {
                GetRoomDto dto= RoomEntityToGetRoomDtoMapper.INSTANCE.apply(room);
                dtos.add(dto);
            }

        });
        return dtos;
    }

    @Override
    @Transactional
    public String leaveRoom(String roomName) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            OAuth2User user = (OAuth2User) auth.getPrincipal();
            String email = user.getAttribute("email");
            UserEntity currUser = userRepository.findByEmail(email);
            RoomEntity room = roomRepository.findByRoomName(roomName);
            currUser.getRooms().remove(room);
            room.getUsers().remove(currUser);
            roomRepository.save(room);
            userRepository.save(currUser);
            return "Room successfully left";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
