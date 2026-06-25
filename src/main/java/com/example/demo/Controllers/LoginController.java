package com.example.demo.Controllers;

import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    final private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> createUser(@PathVariable String username) {

        User user =
                userRepository.save(User.builder()
                        .user_name(username)
                        .build());

        log.debug(user.toString());

        String access =
                JwtUtil.generateToken(user);
        String refresh =
                JwtUtil.generateRefreshToken(user);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("name", user.getUser_name(),
                        "user_id", user.getUser_id().toString(),
                        "Access Token", access,
                        "Refresh Token", refresh
                ));

    }

}
