package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.CommCdDto;
import com.project.LimeRMS.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "UserController", description = "UserController API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @Operation(
            summary = "사용자 로그인",
            description = "사용자의 이메일과 비밀번호를 사용하여 로그인하고 토큰을 발급해준다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userEmail\":\"test1@euclidsoft.co.kr\",\"password\":\"dbzmfflem1!\"}"))))
    public ResponseEntity<?> login(@RequestBody Map<String, String> member) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            String accessToken = userService.login(member);
            resMap.put("res", true);
            resMap.put("msg", accessToken);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/cd-list")
    @Operation(
            summary = "코드 체계별 종류 조회",
            description = "각 코드 체계별로 갖고 있는 종류를 리스트로 반환해준다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"hiCommCd\":\"CD006000\"}"))))
    public ResponseEntity<?> getCommCdList(@RequestBody Map<String, String> input){
        Map<String, Object> resMap = new HashMap<>();
        try {
            List<CommCdDto> commCdDtoList = userService.getCommCdList(input);
            resMap.put("res", true);
            resMap.put("commCds", commCdDtoList);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }


}
