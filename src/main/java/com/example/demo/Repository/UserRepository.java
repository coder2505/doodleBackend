package com.example.demo.Repository;

import com.example.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select u.user_name from users u join user_room ur on ur.user_id " +
            "=u.user_id where ur.room_id = :room_id", nativeQuery = true)
    List<String> memberNames(Long room_id);


}
