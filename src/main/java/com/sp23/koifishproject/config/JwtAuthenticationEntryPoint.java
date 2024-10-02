package com.sp23.koifishproject.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // In ra log khi không có JWT
        System.out.println("JWT token is missing or invalid");

        // Trả về lỗi 401 và nội dung thông báo
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is missing or invalid");
    }
}