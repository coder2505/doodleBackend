package com.example.demo.Controllers;
import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UserRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequestMapping("/get")
@RestController
public class GetDataControllers {

    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    public GetDataControllers(UserRepository userRepository, UserRoomRepository userRoomRepository) {
        this.userRepository = userRepository;
        this.userRoomRepository = userRoomRepository;
    }

    @GetMapping("/roomDetails")
    public Map<String, String> getRoomDetails(@RequestAttribute("user_id") String user_id){


        List<UserRoom> room =
                userRoomRepository.findRoomIDByUserId(UUID.fromString(user_id));

        if(room.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user not " +
                    "in a room");
        }

        Long roomCode = room.getFirst().getRoom().getRoom_id();
        String roomName = room.getFirst().getRoom().getRoom_name();

        Map<String, String> map = new HashMap<>();
        map.put("roomCode", roomCode.toString());
        map.put("roomName", roomName);

        return map;

    }


    @GetMapping("/roomMembers")
    public List<String> getMembers(@RequestAttribute("user_id") String user_id) {


        List<UserRoom> room =
                userRoomRepository.findRoomIDByUserId(UUID.fromString(user_id));

        if(room.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"user not " +
                    "in a room");
        }

        Long roomCode = room.getFirst().getRoom().getRoom_id();

        log.debug("room code"+ roomCode);

        return userRepository.memberNames(roomCode);

    }


}
