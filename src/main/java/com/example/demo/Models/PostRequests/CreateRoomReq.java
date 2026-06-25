package com.example.demo.Models.PostRequests;


import lombok.Data;

@Data
public class CreateRoomReq {

    String access_token;
    String room_name;

}
