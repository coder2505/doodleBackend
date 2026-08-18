package com.example.demo.Repository;

import com.example.demo.Models.Room;
import com.example.demo.Models.UserRoom;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE room set textid = :textid where room_id = :roomId",
            nativeQuery = true)
    void updateResource(@Param("textid") int textid,
                        @Param("roomId") Long roomId);

}
