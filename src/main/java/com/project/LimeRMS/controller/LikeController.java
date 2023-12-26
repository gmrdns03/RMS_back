package com.project.LimeRMS.controller;

import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "LikeController", description = "LikeController API")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final JwtProvider jwtProvider;


    @PostMapping("/likes")
    @Operation(
        summary = "좋아요",
        description = "컨텐츠 좋아요",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\":1}"))))
    public ResponseEntity<?> likes(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, Integer> content) {
        Map<String, Object> resMap = new HashMap<>();
        String likeUserId = jwtProvider.getUserPk(token);

        try {
            String message = likeService.likes(likeUserId,content);
            resMap.put("res", true);
            resMap.put("msg", message);
            return ResponseEntity.ok().body(resMap);
        }
        catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);

        }
    }

}
