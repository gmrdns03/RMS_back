package com.project.LimeRMS.controller;

import com.project.LimeRMS.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        @RequestPart (value = "file") MultipartFile multipartFile
    ) throws IOException {
        try {
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
            Resource resource = new FileSystemResource(filePath);

            return ResponseEntity
                .accepted()
                .body(resource);
        } catch (Exception e) {
            return ResponseEntity.ok()
                .body(e);
        }
    }
}
