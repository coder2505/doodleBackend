package com.example.demo.Controllers;

import com.example.demo.Models.PostRequests.CreateRoomReq;
import com.example.demo.Models.Room;
import com.example.demo.Models.User;
import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.RoomRepository;
import com.example.demo.Repository.UserRoomRepository;
import com.example.demo.Security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    public RoomController(RoomRepository roomRepository,
                          UserRoomRepository userRoomRepository) {
        this.roomRepository = roomRepository;
        this.userRoomRepository = userRoomRepository;
    }


    @PostMapping("/create-room")
    public ResponseEntity<Map<String, String>> createRoom(@RequestBody CreateRoomReq createRoomReq) {

        if (!JwtUtil.isTokenValid(createRoomReq.getAccess_token())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCESS" +
                    " TOKEN HAS EXPIRED");
        }

        String user_id = JwtUtil.getClaims(createRoomReq.getAccess_token())
                .getSubject();
        Room room = roomRepository.save(Room.builder()
                .room_name(createRoomReq.getRoom_name()).build());


        // @formatter:off
        UserRoom userRoom =
                userRoomRepository.save(UserRoom.builder().
                        room(room).
                        user(User.builder().user_id(UUID.fromString(user_id)).user_name("dummy").build())
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("room_id", room.getRoom_id().toString()));

        //@formatter:on


    }

}
