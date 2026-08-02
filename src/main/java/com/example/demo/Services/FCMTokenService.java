package com.example.demo.Services;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Slf4j
@Service
public class FCMTokenService{

    private static final String FCM_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() throws IOException {

        InputStream serviceAccount =
                getClass().getClassLoader().getResourceAsStream("doodleapp-firebase.json");

        if (serviceAccount == null) {
            throw new RuntimeException("Firebase Service account JSON not found!");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount)
                .createScoped(Collections.singleton(FCM_SCOPE));

        credentials.refreshIfExpired();

        AccessToken token = credentials.getAccessToken();

        return token.getTokenValue();
    }


}
