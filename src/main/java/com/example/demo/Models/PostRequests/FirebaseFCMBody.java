package com.example.demo.Models.PostRequests;

import lombok.Builder;
import lombok.Data;

//    {
//        "message":{
//        "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
//        "notification":{
//            "body":"This is an FCM notification message!",
//            "title":"FCM Message"
//          }
//       }
//    }


@Data
@Builder
public class FirebaseFCMBody {

    private Message message;

    @Builder
    @Data
    public static class Message{

        String token;

        private Notification notification;

        @Builder
        @Data
        public static class Notification{

            String body;
            String title;

        }

    }


}
