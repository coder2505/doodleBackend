package com.example.demo.Models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Embeddable
public class UserRoomKey implements Serializable {

    UUID user_id;
    Long room_id;

}
