package io.github.skshiydv.telly.room.controller;

import io.github.skshiydv.telly.core.response.ApiResponse;
import io.github.skshiydv.telly.room.model.CreateRoomDTO;
import io.github.skshiydv.telly.room.model.GetRoomDto;
import io.github.skshiydv.telly.room.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/create-room")
    public ResponseEntity<ApiResponse> createRoom(@RequestBody CreateRoomDTO createRoomDTO) {
        String response = roomService.createRoom(createRoomDTO);
        if (response.equals("ok")) {
            return ResponseEntity.ok(new ApiResponse(true, "Room created successfully."));

        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Fill all fields correctly."));
    }

    @GetMapping
    public List<GetRoomDto> getAllRooms() {
        return roomService.getAllRooms();

    }

    @GetMapping("/{roomName}")
    public ResponseEntity<GetRoomDto> getRoom(@PathVariable String roomName) {
        GetRoomDto res = roomService.getRoomByRoomName(roomName);
        if (res == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/join")
    public ResponseEntity<String> addUser(@RequestBody String roomName) {
        roomService.addUser(roomName);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/curr-user")
    public Object getCurrUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        return user;
    }

    @GetMapping("/user")
    public List<GetRoomDto> getRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/unknown-rooms")
    public ResponseEntity<List<GetRoomDto>> newRoomsSuggestions() {
        return new ResponseEntity<>(roomService.getAllUnjoinedRooms(), HttpStatus.OK);

    }

    @GetMapping("/joined")
    public ResponseEntity<List<GetRoomDto>> getJoinedRooms() {

        return new ResponseEntity<>(roomService.getAllRoomsByUserId(), HttpStatus.OK);
    }

    @GetMapping("/leave/{roomName}")
    public ResponseEntity<String> leaveRoom(@PathVariable String roomName) {
        return new ResponseEntity<>(roomService.leaveRoom(roomName), HttpStatus.OK);
    }

}

