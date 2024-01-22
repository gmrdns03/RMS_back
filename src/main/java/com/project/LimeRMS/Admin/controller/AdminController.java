package com.project.LimeRMS.Admin.controller;

import com.project.LimeRMS.Admin.dto.AuthListDto;
import com.project.LimeRMS.Admin.dto.BoardPriorityDto;
import com.project.LimeRMS.Admin.dto.ContentListDto;
import com.project.LimeRMS.Admin.dto.UserInfoDto;
import com.project.LimeRMS.Board.dto.BoardInfoDto;
import com.project.LimeRMS.Rental.dto.RentalListDto;
import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.Admin.service.AdminService;
import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.User.service.ProfileService;
import com.project.LimeRMS.Rental.service.RentalService;
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
    private final CommonService commonService;

    @PostMapping("/user-detail")
    @Operation(
            summary = "특정 회원 조회",
            description = "관리자는 특정 회원의 정보를 조회할 수 있다",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userId\":\"10\"}"))))
    public ResponseEntity<?> getUserInformation(@RequestBody Map<String, String> member){
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            String userId = member.get("userId");
            UserInfoDto userInfo = profileService.getUserProfileDtl(userId);
            List<RentalListDto> rentalList =  rentalService.getRentalList(userId);
            data.put("userInfo", userInfo);
            data.put("rentalList", rentalList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/users")
    @Operation(
            summary = "모든 회원 조회",
            description = "관리자는 모든 회원의 정보를 조회할 수 있다.")
    public ResponseEntity<?> getAllUserInformation(){
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            List<UserInfoDto> userInfoDtoList = adminService.getAllUserInformation();
            data.put("userList", userInfoDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        try {
            String message = adminService.addUser(managerId, signupInfo);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        try {
            String message = adminService.updateUserProfile(managerId, member);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        try {
            String message = adminService.deleteUserProfile(managerId, member);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        try {
            String message = adminService.resetUserPw(managerId, member);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/auth-list")
    @Operation(
            summary = "모든 권한 종류 조회",
            description = "관리자는 모든 종류의 권한 정보를 조회할 수 있다.")
    public ResponseEntity<?> getAuthenticationList(){
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            List<AuthListDto> authDtoList = adminService.getAuthenticationList();
            data.put("authenticationList", authDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/boards")
    @Operation(
            summary = "모든 보드 정보 불러오기",
            description = "관리자는 모든 보드의 정보를 조회할 수 있다.")
    public ResponseEntity<?> getBoardList(){
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            List<BoardInfoDto> boardInfoDtoList = adminService.getBoardList();
            data.put("boardList", boardInfoDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/overdues")
    @Operation(
            summary = "컨텐츠 연체자 정보 조회",
            description = "관리자는 연체된 컨텐츠의 정보를 확인할 수 있다")
    public ResponseEntity<?> getOverdueContentList(){
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            List<ContentListDto> overdueContentList = adminService.getOverdueContentList();
            data.put("overdueContents", overdueContentList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/overdues/change-stat")
    @Operation(
        summary = "관리자가 컨텐츠 반납 -> 미반납 상태 변경",
        description = "관리자는 사용자가 아직 반납하지 않았는데도 불구하고 상태를 반납으로 변경했을 때 다시 미반납으로 변경할 수 있다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"rentalUserId\":1, \"contentId\":1}"))))
    public ResponseEntity<?> changeContentRentalStat(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> member){
        Map<String, Object> resMap ;
        try {
            String modfUserId = jwtProvider.getUserPk(token);
            String message = adminService.changeContentRentalStat(modfUserId, member);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/overdues/add-change")
    @Operation(
        summary = "컨텐츠 상태 변경후 반납 필요에 대한 알림 추가",
        description = "관리자는 사용자가 아직 반납하지 않았는데도 불구하고 상태를 반납으로 변경했을 때 다시 미반납으로 변경할 수 있다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"userId\":1, \"contentId\":1}"))))
    public ResponseEntity<?> addReReturnNotification(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> member){
        Map<String, Object> resMap ;
        try {
            String regUserId = jwtProvider.getUserPk(token);
            String message = adminService.addReReturnNotification(regUserId, member);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/boards/priorities")
    @Operation(
        summary = "보드 우선순위 변경",
        description = "관리자는 보드의 우선순위를 변경할 수 있다")
    public ResponseEntity<?> changeBoardPriorities(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody List<BoardPriorityDto> boardPriorityDtoList){
        String managerId = jwtProvider.getUserPk(token);
        Map<String, Object> resMap ;
        try {
            String message = adminService.changeBoardPriorities(managerId, boardPriorityDtoList);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

}
