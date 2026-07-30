package com.example.demo.Network;

import com.example.demo.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(Interceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("inside interceptor");

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "auth header Should start with bearer");
        }

        String token = header.substring(7);

        if (!JwtUtil.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ACCESS" +
                    " TOKEN HAS EXPIRED");
        }

        String user_id = JwtUtil.getClaims(token)
                .getSubject();

        request.setAttribute("user_id", user_id);

        return true;


    }
}
