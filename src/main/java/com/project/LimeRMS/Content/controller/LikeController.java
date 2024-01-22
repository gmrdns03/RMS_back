package com.project.LimeRMS.Content.controller;


import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.Content.service.LikeService;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LikeController", description = "LikeController API")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;


    @PostMapping("/likes")
    @Operation(
        summary = "좋아요",
        description = "컨텐츠 좋아요",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\":1}"))))
    public ResponseEntity<?> likes(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> content) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        String likeUserId = jwtProvider.getUserPk(token);
        try {
            String message = likeService.likes(likeUserId,content);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        }
        catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);

        }
    }

    @PutMapping ("/likes/delete")
    @Operation(
        summary = "좋아요 취소",
        description = "컨텐츠 좋아요 취소",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\":1}"))))
    public ResponseEntity<?>cancelLikes(@Parameter(hidden = true) @RequestHeader("AccessToken") String token ,@RequestBody Map<String, Integer> content) {
        Map<String, Object> resMap ;
        String likeUserId = jwtProvider.getUserPk(token);
        try {
            String message = likeService.cancelLikes(likeUserId,content);
            resMap = commonService.makeReturnData(true, 201, message, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);

        }
    }



}
