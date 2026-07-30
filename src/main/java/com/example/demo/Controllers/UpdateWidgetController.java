package com.example.demo.Controllers;

import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.FCMSendDataLoad;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UserRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequestMapping("/update-widget")
@RestController
public class UpdateWidgetController {

    private final UserRoomRepository userRoomRepository;
    private final FCMSendDataLoad fcmSendDataLoad;
    private final UserRepository userRepository;

    public UpdateWidgetController(UserRoomRepository userRoomRepository, FCMSendDataLoad fcmSendDataLoad, UserRepository userRepository) {
        this.userRoomRepository = userRoomRepository;
        this.fcmSendDataLoad = fcmSendDataLoad;
        this.userRepository = userRepository;
    }

    @PostMapping("/text/{payload}")
    public void updateText(@PathVariable("payload") String payload,
                           @RequestAttribute("user_id") String userId) throws IOException {


        List<UserRoom> room =
                userRoomRepository.findRoomIDByUserId(UUID.fromString(userId));

        String user_name =
                userRepository.findById(UUID.fromString(userId)).get().getUser_name();

        Long room_code = room.getFirst().getRoom().getRoom_id();
        List<UserRoom> membersOfRoom = userRoomRepository.findMembersOfRoom(room_code);

        fcmSendDataLoad.SendNotif(membersOfRoom, user_name, payload);


    }

}
