package com.project.LimeRMS.controller;

import com.project.LimeRMS.entity.Authentication;
import com.project.LimeRMS.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    //Test 코드(나중에 연관 코드 삭제 필요)
    @PostMapping("/test")
    @ResponseBody
    public boolean insertAuthentication(@RequestBody Authentication authentication) {
        try {
            authenticationService.insertAuthentication(authentication);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
