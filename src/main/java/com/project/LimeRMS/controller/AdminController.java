package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.*;
import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.AdminService;
import com.project.LimeRMS.service.ProfileService;
import com.project.LimeRMS.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    private final RentalService rentalService;
    private final ProfileService profileService;
    private final JwtProvider jwtProvider;

    @PostMapping("/user-detail")
    @Operation(
            summary = "특정 회원 조회",
            description = "관리자는 특정 회원의 정보를 조회할 수 있다",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userId\":\"10\"}"))))
    public ResponseEntity<?> getUserInformation(@RequestBody Map<String, String> member){
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String userId = member.get("userId");
            UserInfoDto userInfo = profileService.getUserProfileDtl(userId);
            resMap.put("res", true);
            data.put("userInfo", userInfo);
            List<RentalListDto> rentalList =  rentalService.getRentalList(userId);
            data.put("rentalList", rentalList);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/users")
    @Operation(
            summary = "모든 회원 조회",
            description = "관리자는 모든 회원의 정보를 조회할 수 있다.")
    public ResponseEntity<?> getAllUserInformation(){
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            List<UserInfoDto> userInfoDtoList = adminService.getAllUserInformation();
            resMap.put("res", true);
            resMap.put("statusCode", 200);
            data.put("userList", userInfoDtoList);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/users/add")
    @Operation(
            summary = "회원 추가",
            description = "관리자는 회원을 추가할 수 있다(초기 비밀번호는 통일)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userEmail\":\"test1@euclidsoft.co.kr\",\"userNm\":\"김00\",\"phoneNumber\":\"01022223333\",\"authId\":\"9\"}"))))
    public ResponseEntity<?> addUser(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, String> signupInfo) {
        String managerId = jwtProvider.getUserPk(token);
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = adminService.addUser(managerId, signupInfo);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/users/modify")
    @Operation(
            summary = "회원 정보 수정",
            description = "관리자는 회원의 정보를 수정할 수 있다(이름, 권한, 상태, 핸드폰 번호, 비밀번호)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userId\":\"10\",\"userNm\":\"김00\",\"authId\":\"4\",\"userStat\":\"CD006003\",\"phoneNumber\":\"01077778888\"}"))))
    public ResponseEntity<?> updateUserProfile(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, String> member){
        String managerId = jwtProvider.getUserPk(token);
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = adminService.updateUserProfile(managerId, member);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/users/delete-profile")
    @Operation(
        summary = "회원 프로필 이미지 삭제",
        description = "관리자는 회원의 프로파일 이미지를 삭제할 수 있다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"userId\":10}"))))
    public ResponseEntity<?> deleteUserProfile(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> member){
        Integer managerId = Integer.valueOf(jwtProvider.getUserPk(token));
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = adminService.deleteUserProfile(managerId, member);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/users/reset-pw")
    @Operation(
            summary = "회원 비밀번호 초기화",
            description = "관리자는 회원의 비밀번호를 초기화할 수 있다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userId\":10}"))))
    public ResponseEntity<?> resetUserPw(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> member){
        String managerId = jwtProvider.getUserPk(token);
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = adminService.resetUserPw(managerId, member);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/auth-list")
    @Operation(
            summary = "모든 권한 종류 조회",
            description = "관리자는 모든 종류의 권한 정보를 조회할 수 있다.")
    public ResponseEntity<?> getAuthenticationList(){
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            List<AuthListDto> authDtoList = adminService.getAuthenticationList();
            resMap.put("res", true);
            data.put("authenticationList", authDtoList);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/boards")
    @Operation(
            summary = "모든 보드 정보 불러오기",
            description = "관리자는 모든 보드의 정보를 조회할 수 있다.")
    public ResponseEntity<?> getBoardList(){
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            List<BoardInfoDto> boardInfoDtoList = adminService.getBoardList();
            resMap.put("res", true);
            data.put("boardList", boardInfoDtoList);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/overdues")
    @Operation(
            summary = "컨텐츠 연체자 정보 조회",
            description = "관리자는 연체된 컨텐츠의 정보를 확인할 수 있다")
    public ResponseEntity<?> getOverdueContentList(){
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            List<ContentListDto> overdueContentList = adminService.getOverdueContentList();
            resMap.put("res", true);
            data.put("overdueContents", overdueContentList);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/boards/priorities")
    @Operation(
        summary = "보드 우선순위 변경",
        description = "관리자는 보드의 우선순위를 변경할 수 있다")
    public ResponseEntity<?> changeBoardPriorities(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody List<BoardPriorityDto> boardPriorityDtoList){
        String managerId = jwtProvider.getUserPk(token);
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = adminService.changeBoardPriorities(managerId, boardPriorityDtoList);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

}
