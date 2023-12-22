package com.project.LimeRMS.service;

import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.UserMapper;
import java.io.File;

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
    private final UserMapper userMapper;

    public boolean saveProfileImg(String userId, MultipartFile multipartFile) throws Exception {
        // multipartFile이 비어 있지 않으면 진행
        if(multipartFile != null && !multipartFile.isEmpty()) {
            // profile 폴더가 없는경우 폴더 생성
            String profileFolderPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files\\profile";
            Path folderPath = Paths.get(profileFolderPath);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // 보내온 파일 저장
            UUID uuid = UUID.randomUUID();
            String fileNm = uuid + "_" + multipartFile.getOriginalFilename();
            File file = new File(profileFolderPath, fileNm);
            multipartFile.transferTo(file);

            // 기존 userImg가 있는지 확인
            // 유저가 이미 프로필 이미지가 있는경우 해당 파일 삭제
            User user = userMapper.findByUserId(userId);
            String profileImgPath = user.getProfileImg();
            if ((profileImgPath != null) || !profileImgPath.isEmpty()) {
                deleteProfileImg(profileImgPath);
            }

            // userImg 테이블에 업데이트
            String newProfilePath = profileFolderPath + "\\" + fileNm;
            userMapper.updateProfileImgByUserId(userId, newProfilePath);
            return true;
        } else {
            throw new Exception("Not enough elements. Plz check your data");
        }
    }

    public File getProfileImg(String userId) {
        // userImg가 있는지 확인
        User user = userMapper.findByUserId(userId);
        String profileImgPath = user.getProfileImg();

        // 없는 경우 null 반환
        if (profileImgPath.isEmpty()) {
            return null;
        }

        // 경로가 있는 경우 파일 불러오기
        File destFile = new File(profileImgPath);
        return destFile;
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
