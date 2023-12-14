package com.project.LimeRMS.controller;

import com.project.LimeRMS.entity.Authentication;
import com.project.LimeRMS.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AuthenticationController", description = "AuthenticationController API 모음.")
@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    //Test 코드(나중에 연관 코드 삭제 필요)
//    @PostMapping("/test")
//    @ResponseBody
//    public boolean insertAuthentication(@RequestBody Authentication authentication) {
//        try {
//            authenticationService.insertAuthentication(authentication);
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.getMessage());
//            return false;
//        }
//        return true;
//    }

    @GetMapping("/testTest")
    public String example2() {
        return "API";
    }

}
