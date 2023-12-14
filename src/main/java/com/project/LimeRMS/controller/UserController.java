package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "UserController API 모음.")
@RestController
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
    private JwtResponseDto jwtResponseDto;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestParam String userEmail, @RequestParam String password) {
        return userService.login(userEmail, password);
    }

    @GetMapping("/userTest")
    public String example() {
        return "예시 API";
    }

}
