package com.example.demo.Controllers;

import com.example.demo.Models.PostRequests.UpdateText;
import com.example.demo.Models.Room;
import com.example.demo.Models.Text;
import com.example.demo.Models.UserRoom;
import com.example.demo.Repository.*;
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
    private final TextRepository textRepository;

    public UpdateWidgetController(UserRoomRepository userRoomRepository, FCMSendDataLoad fcmSendDataLoad, UserRepository userRepository, RoomRepository roomRepository, TextRepository textRepository) {
        this.userRoomRepository = userRoomRepository;
        this.fcmSendDataLoad = fcmSendDataLoad;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.textRepository = textRepository;
    }

    @GetMapping("/getText")
    public String getWidgetText(@RequestAttribute("user_id") String userId) {

        Long roomId = userRoomRepository.findRoomIDByUserId(UUID.fromString(userId))
                .getFirst().getRoom().getRoom_id();

        Optional<Room> room = roomRepository.findById(roomId);

        if (room.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "roomId " +
                    "does not exist");
        }

        Optional<Text> text =
                textRepository.findById(room.get().getText().getTextid());

        if (text.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "text id " +
                    "does not exist");
        }

        return text.get().getText();

    }

    @PostMapping("/text")
    public void updateText(@RequestBody UpdateText updateText,
                           @RequestAttribute("user_id") String userId) throws IOException {

        String payload = updateText.getPayload();
        String color = updateText.getColor();
        String font = updateText.getColor();

        List<UserRoom> room =
                userRoomRepository.findRoomIDByUserId(UUID.fromString(userId));

        String user_name =
                userRepository.findById(UUID.fromString(userId)).get()
                        .getUser_name();

        Text text =
                textRepository.save(Text.builder().text(payload)
                        .background(color).font(font).build());

        roomRepository.updateResource(text.getTextid(),
                room.getFirst().getRoom().getRoom_id()
        );

        Long room_code = room.getFirst().getRoom().getRoom_id();
        List<UserRoom> membersOfRoom = userRoomRepository.findMembersOfRoom(room_code);

        fcmSendDataLoad.SendNotif(membersOfRoom, user_name, payload);


    }

}
