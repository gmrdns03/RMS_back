package com.project.LimeRMS.controller;

import com.project.LimeRMS.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ProfileController", description = "ProfileController API")
@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping(value = "/profile/user/save-img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "사용자 프로필 등록")
    public ResponseEntity<?> saveProfileImg(
        @RequestPart (value = "file") MultipartFile multipartFile,
        @RequestPart (value = "userId") String userId
    ) throws IOException {
        try {
            System.out.println("===========================");
            System.out.println(userId);
            System.out.println("===========================");
            String filePath = profileService.saveProfileImg(multipartFile);
            System.out.println("===========================");
            System.out.println("filePath : " + filePath);
            System.out.println("===========================");
//            String imgNm = URLEncoder.encode(filePath, "UTF-8");
//            imgNm = imgNm.replace("\\", "\\\\");
//            System.out.println("===========================");
//            Path path = Paths.get(filePath);
//            System.out.println("===========================GET");
//            Resource resource = new InputStreamResource(Files.newInputStream(path));
//            System.out.println("===========================RESOURCE");
//            String str = resource.getFilename();
//            System.out.println("===========================");
//            System.out.println("resource : " + str);
//            System.out.println("===========================");
            File destFile = new File(filePath);
            String mimeType = Files.probeContentType(Paths.get(destFile.getAbsolutePath()));
            System.out.println("===========================");
            System.out.println(mimeType);
            System.out.println("===========================");
            if (mimeType == null) {
                mimeType = "octet-steam";
            }
            Resource rs = new UrlResource(destFile.toURI());
//            Resource resource = new FileSystemResource(filePath);

            return ResponseEntity
                .accepted()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath)
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(rs);
        } catch (Exception e) {
            return ResponseEntity.ok()
                .body(e);
        }
    }

    @GetMapping(value = "/profile/user/test-img")
    @Operation(summary = "이미지 테스트용")
    public ResponseEntity<?> testGetImg() {
        try {
            String filePath = "C:\\Dev\\RMS_back\\src\\main\\resources\\static\\files\\7fc3d960-4c4e-4252-ac79-c788aa29db47_thousand_of_brain.jpg";
            File destFile = new File(filePath);
            String mimeType = Files.probeContentType(Paths.get(destFile.getAbsolutePath()));
            System.out.println("===========================");
            System.out.println(mimeType);
            System.out.println("===========================");
            if (mimeType == null) {
                mimeType = "octet-steam";
            }
            Resource rs = new UrlResource(destFile.toURI());
//            Resource resource = new FileSystemResource(filePath);

            return ResponseEntity
                .accepted()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath)
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(rs);
        } catch (Exception e) {
            return ResponseEntity.ok()
                .body(e);
        }

    }
}
