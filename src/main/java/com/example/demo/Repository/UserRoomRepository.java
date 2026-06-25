package com.example.demo.Repository;

import com.example.demo.Models.UserRoom;
import com.example.demo.Models.UserRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomRepository extends JpaRepository<UserRoom, UserRoomKey> {
}
