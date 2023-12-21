package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.AuthListDto;
import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "AdminController", description = "AdminController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @Operation(
            summary = "모든 회원 조회",
            description = "관리자는 모든 회원의 정보를 조회할 수 있다.")
    public List<UserInfoDto> getUserInformation(){
        return adminService.getUserInformation();
    }

    @PostMapping("/users/add")
    @Operation(
            summary = "회원 추가",
            description = "관리자는 회원을 추가할 수 있다(초기 비밀번호는 통일)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userEmail\":\"test1@euclidsoft.co.kr\",\"userNm\":\"김00\",\"phoneNumber\":\"01022223333\",\"authId\":\"9\"}")
                    )
            )
    )
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> signupInfo) {
        Map<String, Object> resMap = new HashMap<>();
        String message = adminService.addUser(signupInfo);
        resMap.put("res", message);

        return ResponseEntity
                .ok()
                .body(resMap);
    }

    @PostMapping("/users/modify")
    @Operation(
            summary = "회원 정보 수정",
            description = "관리자는 회원의 정보를 수정할 수 있다(이름, 권한, 상태, 핸드폰 번호, 비밀번호)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userNm\":\"김00\",\"authId\":\"4\",\"userStat\":\"CD006003\",\"phoneNumber\":\"01077778888\", \"password\":\"newPassword!\"}")
                    )
            )
    )

    @GetMapping("/auth-list")
    public List<AuthListDto> getAuthenticationList(){
        return adminService.getAuthenticationList();
    }

}
