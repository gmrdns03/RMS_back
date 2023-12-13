package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private UserService userService;
    private JwtResponseDto jwtResponseDto;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestParam String userEmail, @RequestParam String password) {
        return userService.login(userEmail, password);
    }
}
