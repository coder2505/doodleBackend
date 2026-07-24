package com.example.demo.Controllers;

import com.example.demo.Models.PostRequests.RefreshReq;
import com.example.demo.Models.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    final private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody RefreshReq refreshReq) {

        String token = refreshReq.getRefreshToken();

        if (!JwtUtil.isTokenValid(token)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Request Token not valid"
            );

        }

        String user_id = JwtUtil.getClaims(token).getSubject();
        String access =
                JwtUtil.generateToken(User.builder()
                        .user_id(UUID.fromString(user_id)).build());


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "Access Token", access
                ));


    }

    @PostMapping("/user/{username}/{fcmToken}")
    public ResponseEntity<Map<String, String>> createUser(@PathVariable String username, @PathVariable String fcmToken) {

        log.debug("req came in");

        User user =
                userRepository.save(User.builder()
                        .user_name(username)
                        .fcm_token(fcmToken)
                        .build());

        log.debug(user.toString());

        String access =
                JwtUtil.generateToken(user);
        String refresh =
                JwtUtil.generateRefreshToken(user);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("R", user.getUser_name(),
                        "user_id", user.getUser_id().toString(),
                        "Access Token", access,
                        "Refresh Token", refresh
                ));

    }

}
