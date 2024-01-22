package com.project.LimeRMS.Config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class.getName());
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        log.info("AccessDenied error: {}", accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, String> body = new HashMap<>();
        body.put("status", String.valueOf(HttpServletResponse.SC_OK));
        body.put("error", "Forbidden");
        body.put("message", accessDeniedException.getMessage());
        body.put("path", request.getServletPath());

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
