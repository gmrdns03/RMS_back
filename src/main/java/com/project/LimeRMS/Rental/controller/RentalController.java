package com.project.LimeRMS.Rental.controller;

import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.Rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "RentalController", description = "RentalController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {
    private final JwtProvider jwtProvider;
    private final RentalService rentalService;
    private final CommonService commonService;

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
            resMap = commonService.makeReturnData(true, 201, "대여가 완료되었습니다.", true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
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
        Map<String, Object> resMap ;
        try {
            Integer loginUserId = Integer.valueOf(jwtProvider.getUserPk(token));
            Integer userId = Integer.valueOf(body.get("userId"));
            Integer contentId = Integer.valueOf(body.get("contentId"));
            rentalService.rentalContentReturn(loginUserId, userId, contentId);
            resMap = commonService.makeReturnData(true, 201, "반납이 완료되었습니다.", true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping("/update")
    @Operation(
        summary = "모든 대여 예정일이 지난 컨텐츠의 상태를 '대여'->'연체'로 변경",
        description = "모든 대여 예정일이 지난 컨텐츠의 상태를 '대여'->'연체'로 변경할 수 있다")
    public ResponseEntity<?> changeContentRentalStat(){
        Map<String, Object> resMap ;
        try {
            String message = rentalService.changeContentRentalStat();
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/extensions")
    @Operation(
        summary = "대여 연장",
        description = "대여 연장 횟수를 확인한 후 연장 진행.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\": \"1\", \"userId\": \"1\"}")
            )
        )
    )
    public ResponseEntity<?> contentRentalExtension(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token ,
        @RequestBody Map<String, String> content)
    {
        Map<String, Object> resMap ;
        try {

            String loginUserId = jwtProvider.getUserPk(token);
            String userId = String.valueOf(content.get("userId"));
            String contentId = content.get("contentId");
            String message = rentalService.contentRentalExtension(loginUserId,userId,contentId);
            resMap = commonService.makeReturnData(true, 200, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }







}
