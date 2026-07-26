package com.example.demo.Controllers;

import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.FCMSendDataLoad;
import com.example.demo.Repository.UserRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequestMapping("/update-widget")
@RestController
public class UpdateWidgetController {

    private final UserRoomRepository userRoomRepository;
    private final FCMSendDataLoad fcmSendDataLoad;

    public UpdateWidgetController(UserRoomRepository userRoomRepository, FCMSendDataLoad fcmSendDataLoad) {
        this.userRoomRepository = userRoomRepository;
        this.fcmSendDataLoad = fcmSendDataLoad;
    }


    @PostMapping("/text/{user-id}")
    public void updateText(@PathVariable("user-id") UUID userId) throws IOException {

        List<UserRoom> room = userRoomRepository.findRoomIDByUserId(userId);
        Long room_code = room.getFirst().getRoom().getRoom_id();
        List<UserRoom> membersOfRoom = userRoomRepository.findMembersOfRoom(room_code);

        fcmSendDataLoad.SendNotif(membersOfRoom);


    }

}
