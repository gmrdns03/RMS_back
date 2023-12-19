package com.project.LimeRMS.service;

import java.io.File;
import java.io.IOException;

import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
    public String saveProfileImg(MultipartFile multipartFile) throws IOException {
        if(multipartFile != null && !multipartFile.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();
            String fileNm = uuid + "_" + multipartFile.getOriginalFilename();
            File file = new File(projectPath, fileNm);
            multipartFile.transferTo(file);
            System.out.println("===========================");
            System.out.println(projectPath + fileNm);
            System.out.println("===========================");
            return projectPath + "\\" + fileNm;
        } else {
            return "empty";
        }

    }
}
