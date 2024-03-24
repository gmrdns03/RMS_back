package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.LikeListDto;
import com.project.LimeRMS.dto.RentalListDto;
import com.project.LimeRMS.dto.ReservationListDto;
import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.service.CommonService;
import com.project.LimeRMS.service.LikeService;
import com.project.LimeRMS.service.ProfileService;
import com.project.LimeRMS.service.RentalService;
import com.project.LimeRMS.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
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
    private final LikeService likeService;
    private final ReservationService reservationService;
    private final RentalService rentalService;
    private final CommonService commonService;

    @GetMapping(value = "/user")
    @Operation(summary = "사용자 회원정보 상세조회")
    public ResponseEntity<?> getUserProfileDtl(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            UserInfoDto userInfoDto =  profileService.getUserProfileDtl(userId);
            data.put("userInfo", userInfoDto);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping(value = "/user/save-pwd")
    @Operation(summary = "사용자 비밀번호 변경 저장",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"password\":\"dbzmfflem1!\"}")))
    )
    public ResponseEntity<?> saveUserPassword(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @RequestBody Map<String, String> map
    ) {
        Map<String, Object> resMap ;
        try {
            String userId = jwtProvider.getUserPk(token);
            String password = map.get("password");
            profileService.saveProfilePwd(userId, password);
            resMap = commonService.makeReturnData(true, 201, "password successfully updated", true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);

        }

    }

    @PostMapping(value = "/user/save-img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사용자 프로필 이미지 등록")
    public ResponseEntity<?> saveProfileImg(
        @RequestPart (value = "file") MultipartFile multipartFile,
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token
    ) throws IOException {
        Map<String, Object> resMap ;
        try {
            // 토큰에서 userId 추출
            String userId = jwtProvider.getUserPk(token);
            // 이미지 저장
            profileService.saveProfileImg(userId, multipartFile);
            resMap = commonService.makeReturnData(true, 201, "Registered a new profile image successfully", true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @GetMapping(value = "/user/img")
    @Operation(summary = "사용자 프로필 이미지 가져오기")
    public ResponseEntity<?> getProfileImg(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token) {
        Map<String, Object> resMap ;
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
        } catch (FileNotFoundException e) {
            resMap = commonService.makeReturnData(false, 204, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/likes")
    @Operation(
        summary = "사용자의 관심 목록",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"userId\":1}")
            )
        )
    )
    public ResponseEntity<?> getUserLikesList(
        @RequestBody Map<String, String> body
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            // body에서 userId 추출
            String userId = body.get("userId");

            // 사용자의 관심 목록 받아오기
            List<LikeListDto> likeListDtos = likeService.getUserLikeList(userId);

            data.put("likeList", likeListDtos);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/reservations")
    @Operation(
        summary = "사용자의 예약 목록",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"userId\":1}")
            )
        )
    )
    public ResponseEntity<?> getUserReservatioinList(
        @RequestBody Map<String, String> body
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            // body에서 userId 추출
            String userId = body.get("userId");
            // 사용자 예약 목록 받아오기
            List<ReservationListDto> reservationInfoList = reservationService.getReservationList(userId);
            data.put("reservationInfo", reservationInfoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/rentals")
    @Operation(
        summary = "사용자의 대여 목록",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"userId\":1}")
            )
        )
    )
    public ResponseEntity<?> getUserRentalList(
        @RequestBody Map<String, String> body
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            // body에서 userId 추출
            String userId = body.get("userId");
            List<RentalListDto> rentalListDtoList =  rentalService.getRentalList(userId);
            data.put("rentalInfo", rentalListDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
