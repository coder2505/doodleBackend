package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "user_room")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoom {

    @EmbeddedId
    @Builder.Default
    UserRoomKey userRoomKey = new UserRoomKey();

    @ManyToOne
    @MapsId("room_id")
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;


}
