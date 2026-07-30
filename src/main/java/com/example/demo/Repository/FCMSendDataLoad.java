package com.example.demo.Repository;

import com.example.demo.Configurations.WebClientConfig;
import com.example.demo.Models.PostRequests.FirebaseFCMBody;
import com.example.demo.Models.UserRoom;
import com.example.demo.Services.FCMTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Slf4j
@Repository
public class FCMSendDataLoad {

    private final WebClientConfig webClient;
    private final FCMTokenService fcmTokenService;

    public FCMSendDataLoad(WebClientConfig webClient, FCMTokenService fcmTokenService) {
        this.webClient = webClient;
        this.fcmTokenService = fcmTokenService;
    }

    public void SendNotif(List<UserRoom> membersOfRoom, String userName,
                          String payload) throws IOException {

        String fcmToken = fcmTokenService.getAccessToken();

        for(UserRoom r : membersOfRoom){

            FirebaseFCMBody body = FirebaseFCMBody.builder().message(
                    FirebaseFCMBody.Message.builder().token(r.getUser().getFcm_token())
                            .notification(
                                    FirebaseFCMBody.Message.Notification.builder().body(userName+"sent a message")
                                            .title(payload).build()

                            ).build()
            ).build();

            webClient.webClient().post().uri("/")
                    .header("Authorization", "Bearer "+fcmToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body).retrieve().toBodilessEntity().block();

        }


        log.debug(membersOfRoom.toString());


    }

}
