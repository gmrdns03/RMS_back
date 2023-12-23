package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ProfileController", description = "ProfileController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final JwtProvider jwtProvider;

    @GetMapping(value = "/user")
    @Operation(summary = "사용자 회원정보 상세조회")
    public ResponseEntity<?> getUserProfileDtl(
        @RequestHeader("AccessToken") String token
    ) {
        Map<String, Object> resMap = new HashMap<>();
        String userId = jwtProvider.getUserPk(token);
        UserInfoDto userInfoDto =  profileService.getUserProfileDtl(userId);
        resMap.put("userInfo", userInfoDto);
        return ResponseEntity.ok().body(resMap);
    }

    @PostMapping(value = "/user/save-pwd")
    @Operation(summary = "사용자 비밀번호 변경 저장")
    public ResponseEntity<?> saveUserPassword(
        @RequestHeader("AccessToken") String token,
        @RequestBody Map<String, String> map
    ) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            String password = map.get("password");
            profileService.saveProfilePwd(userId, password);
            resMap.put("res", true);
            resMap.put("msg", "password successfully updated");
            return ResponseEntity.accepted().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resMap);

        }

    }

    @PostMapping(value = "/user/save-img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사용자 프로필 이미지 등록")
    public ResponseEntity<?> saveProfileImg(
        @RequestPart (value = "file") MultipartFile multipartFile,
        @RequestHeader("AccessToken") String token
    ) throws IOException {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try {
            // 토큰에서 userId 추출
            String userId = jwtProvider.getUserPk(token);
            // 이미지 저장
            profileService.saveProfileImg(userId, multipartFile);
            resMap.put("res", true);
            resMap.put("msg", "Registered a new profile image successfully");
            return ResponseEntity.accepted().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping(value = "/user/img")
    @Operation(summary = "사용자 프로필 이미지 가져오기")
    public ResponseEntity<?> getProfileImg(@RequestHeader("AccessToken") String token) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            // 토큰에서 userId 추출
            String userId = jwtProvider.getUserPk(token);

            // 파일 가져오기
            File destFile = profileService.getProfileImg(userId);

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
            resMap.put("res", null);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.badRequest().body(resMap);
        }

    }
}
