package com.project.LimeRMS.controller;

import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.CommonService;
import com.project.LimeRMS.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ContentController", description = "ContentController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentController {
    private final JwtProvider jwtProvider;
    private final ContentService contentService;
    private final CommonService commonService;

    @GetMapping(value = "/{contentId}")
    @Operation(summary = "컨텐츠 상세")
    public ResponseEntity<?> getContentDtl(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @PathVariable("contentId") Integer contentId
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            Map<String, Object> contentDtlRes = contentService.getContentDtail(loginUserId, contentId);
            data.putAll(contentDtlRes);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (NullPointerException e) {
            resMap = commonService.makeReturnData(false, 404, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        } catch (IllegalAccessException e) {
            resMap = commonService.makeReturnData(false, 403, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping("/{contentId}/img")
    public ResponseEntity<?> getContentImg(@PathVariable("contentId") Integer contentId) {
        Map<String, Object> resMap ;
        try {
            File destFile = contentService.getContentImg(contentId);

            // header 생성
            String mimeType = Files.probeContentType(Paths.get(destFile.getAbsolutePath()));
            if (mimeType == null) {
                mimeType = "octet-steam";
            }
            Resource rs = new UrlResource(destFile.toURI());
            return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; \"")
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(rs);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 404, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping(value = "/{contentId}/save-img")
    @Operation(summary = "컨텐츠 대표 이미지 등록")
    public ResponseEntity<?> saveContentImg(
        @RequestPart(value = "file")MultipartFile multipartFile,
        @PathVariable("contentId") Integer contentId,
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token
    ) {
        Map<String, Object> resMap;
        try {
            // 토큰에서 userId 추출
            String loginUserId = jwtProvider.getUserPk(token);
            // 이미지 저장
            contentService.saveContentImg(loginUserId, contentId, multipartFile);
            String msg = "Registered a new profile image successfully";
            resMap = commonService.makeReturnData(true, 201, msg, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
