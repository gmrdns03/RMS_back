package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.BoardPriorityDto;
import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "RentalController", description = "RentalController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final JwtProvider jwtProvider;
    private final RentalService rentalService;

    @PostMapping
    @Operation(summary = "컨텐츠 대여",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @io.swagger.v3.oas.annotations.media.Content(
            examples = @ExampleObject(value = "{\"contentId\": \"1\", \"userId\": \"1\"}")
            )
        )
    )
    public ResponseEntity<?> rental(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @RequestBody Map<String, String> body
    ) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            Integer loginUserId = Integer.valueOf(jwtProvider.getUserPk(token));
            Integer userId = Integer.valueOf(body.get("userId"));
            Integer contentId = Integer.valueOf(body.get("contentId"));
            rentalService.rental(loginUserId, userId, contentId);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", "대여가 완료되었습니다.");
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping("/return")
    @Operation(summary = "대여한 컨텐츠 반납",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\": \"1\", \"userId\": \"1\"}")
            )
        )
    )
    public ResponseEntity<?> rentalContentRetrun(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @RequestBody Map<String, String> body
    ) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            Integer loginUserId = Integer.valueOf(jwtProvider.getUserPk(token));
            Integer userId = Integer.valueOf(body.get("userId"));
            Integer contentId = Integer.valueOf(body.get("contentId"));
            rentalService.rentalContentReturn(loginUserId, userId, contentId);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", "반납이 완료되었습니다.");
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statuscode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping("/update")
    @Operation(
        summary = "모든 대여 예정일이 지난 컨텐츠의 상태를 '대여'->'연체'로 변경",
        description = "모든 대여 예정일이 지난 컨텐츠의 상태를 '대여'->'연체'로 변경할 수 있다")
    public ResponseEntity<?> changeContentRentalStat(){
        Map<String, Object> resMap = new HashMap<>();
        try {
            String message = rentalService.changeContentRentalStat();
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

    @PostMapping("/extensions")
    @Operation(
        summary = "대여 연장",
        description = "대여 연장 횟수를 확인한 후 연장 진행.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"hiCommCd\":\"CD006000\"}"))))
    public ResponseEntity<?> contentRentalExtension(@RequestHeader("AccessToken") String token ,@RequestBody Map<String, Integer> rental) {
        Map<String, Object> resMap = new HashMap<>();
        String rentalUserId = jwtProvider.getUserPk(token);
        try {
//            String message = rentalService.contentRentalExtension();
//            resMap.put("res", true);
//            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }







}
