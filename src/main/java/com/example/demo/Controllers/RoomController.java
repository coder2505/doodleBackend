package com.example.demo.Controllers;

import com.example.demo.Models.PostRequests.CreateRoomReq;
import com.example.demo.Models.Room;
import com.example.demo.Models.User;
import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.RoomRepository;
import com.example.demo.Repository.UserRoomRepository;
import com.example.demo.Security.JwtUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class RoomController {

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    public RoomController(RoomRepository roomRepository,
                          UserRoomRepository userRoomRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
    }

    @PostMapping("/create-room/{roomName}")
    public ResponseEntity<Map<String, String>> createRoom(@PathVariable String roomName, @RequestHeader("Authorization") String accessToken) {

        if (!accessToken.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "auth header Should start with bearer");
        }

        String token = accessToken.substring(7);

        if (!JwtUtil.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCESS" +
                    " TOKEN HAS EXPIRED");
        }

        String user_id = JwtUtil.getClaims(token)
                .getSubject();
        Room room = roomRepository.save(Room.builder()
                .room_name(roomName).build());


        // @formatter:off
        UserRoom userRoom =
                userRoomRepository.save(UserRoom.builder().
                        room(room).
                        user(User.builder().user_id(UUID.fromString(user_id)).user_name("dummy").build())
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("room_id", room.getRoom_id().toString()));

        //@formatter:on


    }

    // using same request body, assuming room_name as room_id.
    @PostMapping("/join-room/{roomId}")
    public ResponseEntity<Map<String, String>> joinRoom(@PathVariable String roomId, @RequestHeader("Authorization") String accessToken) {

        log.debug(roomId);

        if (!accessToken.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "auth header Should start with bearer");
        }
        String token = accessToken.substring(7);
        Long room_id = Long.valueOf(roomId.strip());

        if (!roomRepository.existsById(room_id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error",
                    "room id does not exist"));
        }

        String user_id = JwtUtil.getClaims(token)
                .getSubject();


        // @formatter:off
        UserRoom userRoom =
                userRoomRepository.save(UserRoom.builder().
                        room(Room.builder().room_id(room_id).room_name("dummy").build()).
                        user(User.builder().user_id(UUID.fromString(user_id)).user_name("dummy").build())
                        .build());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("room_id",
                room_id.toString()));

        //@formatter:on


    }

}
