package com.project.LimeRMS.service;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
    public String saveProfileImg(MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()) {
            String profileFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\profile";
            Path folderPath = Paths.get(profileFolderPath);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            UUID uuid = UUID.randomUUID();
            String fileNm = uuid + "_" + multipartFile.getOriginalFilename();
            File file = new File(profileFolderPath, fileNm);
            multipartFile.transferTo(file);
            return profileFolderPath + "\\" + fileNm;
        } else {
            return "";
        }
    }

    public boolean deleteProfileImg(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("파일 삭제 성공");
                return true;
            } else {
                System.out.println("파일 삭제 실패");
                return false;
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
            return true;
        }
    }
}
