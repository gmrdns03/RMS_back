package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.AuthenticationDto;
import com.project.LimeRMS.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping("/test")
    @ResponseBody
    public boolean insertAuthenticationDto(@RequestBody AuthenticationDto authenticationDto) {
        try {
            return authenticationService.insertDto(authenticationDto);
        } catch (Exception e) {
            // 예외 처리를 여기에 추가할 수 있습니다.

            return false;
        }
    }

}
