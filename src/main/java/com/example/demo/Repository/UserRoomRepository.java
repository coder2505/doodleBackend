package com.example.demo.Repository;

import com.example.demo.Models.UserRoom;
import com.example.demo.Models.UserRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRoomRepository extends JpaRepository<UserRoom, UserRoomKey> {

    @Query("SELECT u FROM UserRoom u WHERE u.userRoomKey.user_id = :userId")
    List<UserRoom> findRoomIDByUserId(@Param("userId") UUID userId);


    @Query("SELECT u FROM UserRoom u WHERE u.userRoomKey.room_id = :roomId")
    List<UserRoom> findMembersOfRoom(@Param("roomId") Long roomId);

}
