package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.NotiDto;
import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "NotificationController", description = "NotificationController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtProvider jwtProvider;

    @GetMapping
    @Operation(
        summary = "특정 사용자의 알림 불러오기",
        description = "사용자가 읽은 알림을 미확인 -> 확인 상태로 변경한다.")
    public ResponseEntity<?> getAllNotification(@Parameter(hidden = true) @RequestHeader("AccessToken")String token){
        Map<String, Object> resMap = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            List<NotiDto> notiDtoList = notificationService.getAllNotification(userId);
            resMap.put("res", true);
            resMap.put("notiList", notiDtoList);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }


//    @PutMapping("/add-return")
//    @Operation(
//        summary = "대여일과 반납 예정일을 비교하여 알림 추가",
//        description = "반납 예정일 3일전부터 매일 알람을 추가(같은 상태의 ~~가 있다면 제일 최근1건만 표기)")
//    public ResponseEntity<?> changeContentRentalStat(){
//        Map<String, Object> resMap = new HashMap<>();
//        try {
//            String message = rentalService.changeContentRentalStat();
//            resMap.put("res", true);
//            resMap.put("msg", message);
//            return ResponseEntity.ok().body(resMap);
//        } catch (Exception e) {
//            resMap.put("res", false);
//            resMap.put("msg", e.getMessage());
//            return ResponseEntity.ok().body(resMap);
//        }
//    }

    @PostMapping("/update")
    @Operation(
        summary = "알림 상태 업데이트 (미확인 -> 확인)",
        description = "사용자가 읽은 알림을 미확인 -> 확인 상태로 변경한다.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"notiId\":1}"))))
    public ResponseEntity<?> changeContentRentalStat(@RequestBody Map<String, Integer> alarm){
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = notificationService.changeContentRentalStat(alarm);
            resMap.put("res", true);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping("/add-overdues")
    @Operation(
        summary = "연체된 컨텐츠 알림 목록에 추가 (매일)",
        description = "연체된 컨테츠를 반납하도록 사용자에게 안내하는 알림을 목록에 추가한다.")
    public ResponseEntity<?> addOverdueNotification(@Parameter(hidden = true) @RequestHeader("AccessToken")String token){
        Map<String, Object> resMap = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            String message = notificationService.addOverdueNotification(userId);
            resMap.put("res", true);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }
}
