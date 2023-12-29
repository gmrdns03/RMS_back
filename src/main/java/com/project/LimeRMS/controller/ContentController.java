package com.project.LimeRMS.controller;

import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ContentController", description = "ContentController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {
    private final JwtProvider jwtProvider;
    private final ContentService contentService;
    @GetMapping(value = "/{contentId}")
    @Operation(summary = "컨텐츠 상세")
    public ResponseEntity<?> getContentDtl(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @PathVariable("contentId") Integer contentId
    ) {
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            Map<String, Object> contentDtlRes = contentService.getContentDtail(loginUserId, contentId);
            data.putAll(contentDtlRes);
            resMap.put("res", true);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (NullPointerException e) {
            resMap.put("res", false);
            resMap.put("statusCode", 404);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        } catch (IllegalAccessException e) {
            resMap.put("res", false);
            resMap.put("statusCode", 403);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }
}
