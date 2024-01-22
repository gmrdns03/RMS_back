package com.project.LimeRMS.User.controller;

import com.project.LimeRMS.User.dto.CommCdDto;
import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.User.service.UserService;
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
    private final CommonService commonService;

    @PostMapping("/login")
    @Operation(
            summary = "사용자 로그인",
            description = "사용자의 이메일과 비밀번호를 사용하여 로그인하고 토큰을 발급해준다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userEmail\":\"test1@euclidsoft.co.kr\",\"password\":\"dbzmfflem1!\"}"))))
    public ResponseEntity<?> login(@RequestBody Map<String, String> member) {
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String accessToken = userService.login(member);
            data.put("AccessToken", accessToken);
            resMap = commonService.makeReturnData(true, 200, "로그인이 완료되었습니다.", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 401, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            List<CommCdDto> commCdDtoList = userService.getCommCdList(input);
            data.put("commCds", commCdDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }


}
