package com.example.demo.Controllers;

import com.example.demo.Models.Room;
import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.FCMSendDataLoad;
import com.example.demo.Repository.RoomRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UserRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequestMapping("/widget")
@RestController
public class UpdateWidgetController {

    private final UserRoomRepository userRoomRepository;
    private final FCMSendDataLoad fcmSendDataLoad;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public UpdateWidgetController(UserRoomRepository userRoomRepository, FCMSendDataLoad fcmSendDataLoad, UserRepository userRepository, RoomRepository roomRepository) {
        this.userRoomRepository = userRoomRepository;
        this.fcmSendDataLoad = fcmSendDataLoad;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/getText")
    public String getWidgetText(@RequestAttribute("user_id") String userId){

        Long roomId = userRoomRepository.findRoomIDByUserId(UUID.fromString(userId)).getFirst().getRoom().getRoom_id();

        Optional<Room> room = roomRepository.findById(roomId);

        if(room.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"roomId " +
                    "does not exist");
        }

        return room.get().getResource();


    }

    @PostMapping("/text/{payload}")
    public void updateText(@PathVariable("payload") String payload,
                           @RequestAttribute("user_id") String userId) throws IOException {


        List<UserRoom> room =
                userRoomRepository.findRoomIDByUserId(UUID.fromString(userId));

        String user_name =
                userRepository.findById(UUID.fromString(userId)).get()
                        .getUser_name();

        roomRepository.updateResource(payload,
                room.getFirst().getRoom().getRoom_id()
        );

        Long room_code = room.getFirst().getRoom().getRoom_id();
        List<UserRoom> membersOfRoom = userRoomRepository.findMembersOfRoom(room_code);

        fcmSendDataLoad.SendNotif(membersOfRoom, user_name, payload);


    }

}
