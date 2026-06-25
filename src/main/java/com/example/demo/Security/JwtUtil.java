package com.example.demo.Security;

import com.example.demo.Models.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {

        public static String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getUser_id().toString())
                .expiration(new Date(System.currentTimeMillis() + 900_000)) // 15 minutes.
                .signWith(getSigningKey())
                .compact();

    }

    public static String generateRefreshToken(User user) {

           LocalDate date =
                   LocalDate.now().plusDays(15);
           ZoneId defaultZoneId = ZoneId.systemDefault();

        return Jwts.builder()
                .subject(user.getUser_id().toString())
                .expiration(Date.from(date.atStartOfDay(defaultZoneId).toInstant())) // 15 days
                .signWith(getSigningKey())
                .compact();

    }

    public static Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private static boolean isTokenValid(String token) {
        return !isExpired(token);
    }

    private static boolean isExpired(String token) {

        return getClaims(token).getExpiration()
                .before(new Date());

    }

    public static SecretKey getSigningKey() {
        byte[] array =
                Decoders.BASE64.decode(Dotenv.load()
                        .get("SIGNING_KEY"));
        return Keys.hmacShaKeyFor(array);

    }

}
