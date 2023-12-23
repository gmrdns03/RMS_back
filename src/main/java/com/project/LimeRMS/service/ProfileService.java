package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.UserMapper;
import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserMapper userMapper;
    private final CommCdMapper commCdMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${filesPath.profile}")
    private String DtlProfilePath;

    public UserInfoDto getUserProfileDtl(String id) {
        User user = userMapper.findByUserId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        Integer userId = user.getUserId();
        String userNm = user.getUserNm();
        String userEmail = user.getUserEmail();
        String joinDt = formatter.format(user.getJoinDt());
        String authNm = user.getAuthentication().getAuthNm();
        String userStat = user.getUserStat();
        String userStatNm = commCdMapper.findCdNmByCd(userStat);
        String phoneNumber = user.getPhoneNumber();
        return new UserInfoDto(userId, userNm, userEmail, joinDt, authNm, userStat, userStatNm, phoneNumber);
    }

    public void saveProfilePwd(String id, String password) throws Exception {
        Map<String, Object> validateRes =  isValidPassword(password);
        Boolean res = (Boolean) validateRes.get("res");
        if (res) {
            String encodedPwd = passwordEncoder.encode(password);
            Integer userId = Integer.valueOf(id);
            userMapper.updatePwByUserId(userId, encodedPwd, userId);
        } else {
            throw new Exception(String.valueOf(validateRes.get("msg")));
        }
    }

    public boolean saveProfileImg(String userId, MultipartFile multipartFile) throws Exception {
        // multipartFile이 비어 있지 않으면 진행
        if(multipartFile != null && !multipartFile.isEmpty()) {
            // profile 폴더가 없는경우 폴더 생성
            String profileFolderPath = System.getProperty("user.dir") + DtlProfilePath;
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
            String profileImgPath = userMapper.findProfileImgByUserId(Integer.valueOf(userId));
            if ((profileImgPath != null) && !profileImgPath.isEmpty()) {
                deleteProfileImg(profileImgPath);
            }

            // userImg 테이블에 업데이트
            String newProfilePath = profileFolderPath + "/" + fileNm;
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

    public Map<String, Object> isValidPassword(String password) {
        Map<String, Object> resMap = new HashMap<>();
        final int MIN = 8;
        final int MAX = 20;

        // 영어, 숫자, 특수문자 포함한 MIN to MAX 글자 정규식
        final String REGEX = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";
        // 3자리 연속 문자 정규식
        final String SAMEPT = "(\\w)\\1\\1";
        // 공백 문자 정규식
        final String BLANKPT = "(\\s)";

        // 정규식 검사객체
        Matcher matcher;

        // 공백 체크
        if (password == null || password.isEmpty()) {
            resMap.put("res", false);
            resMap.put("msg", "Detected: No Password");
            return resMap;
        }

        // ASCII 문자 비교를 위한 UpperCase
        String tmpPw = password.toUpperCase();
        // 문자열 길이
        int strLen = tmpPw.length();

        // 글자 길이 체크
        if (strLen > 20 || strLen < 8) {
            resMap.put("res", false);
            resMap.put("msg", "Detected: Incorrect Length(Length: " + strLen + ")");
            return resMap;
        }

        // 공백 체크
        matcher = Pattern.compile(BLANKPT).matcher(tmpPw);
        if (matcher.find()) {
            resMap.put("res", false);
            resMap.put("msg", "Detected: Blank");
            return resMap;
        }

        // 비밀번호 정규식 체크
        matcher = Pattern.compile(REGEX).matcher(tmpPw);
        if (!matcher.find()) {
            resMap.put("res", false);
            resMap.put("msg", "Detected: Wrong Regex");
            return resMap;
        }

        // 동일한 문자 3개 이상 체크
        matcher = Pattern.compile(SAMEPT).matcher(tmpPw);
        if (matcher.find()) {
            resMap.put("res", false);
            resMap.put("msg", "Detected: Same Word");
            return resMap;
        }

        // 연속된 문자 / 숫자 3개 이상 체크
        // ASCII Char를 담을 배열 선언
        int[] tmpArray = new int[strLen];

        // Make Array
        for (int i = 0; i < strLen; i++) {
            tmpArray[i] = tmpPw.charAt(i);
        }

        // Validation Array
        for (int i = 0; i < strLen - 2; i++) {
            // 첫 글자 A-Z / 0-9
            if ((tmpArray[i] > 47
                && tmpArray[i + 2] < 58)
                || (tmpArray[i] > 64
                && tmpArray[i + 2] < 91)) {
                // 배열의 연속된 수 검사
                // 3번째 글자 - 2번째 글자 = 1, 3번째 글자 - 1번째 글자 = 2
                if (Math.abs(tmpArray[i + 2] - tmpArray[i + 1]) == 1
                    && Math.abs(tmpArray[i + 2] - tmpArray[i]) == 2) {
                    char c1 = (char) tmpArray[i];
                    char c2 = (char) tmpArray[i + 1];
                    char c3 = (char) tmpArray[i + 2];
                    resMap.put("res", false);
                    resMap.put("msg", "Detected: Continuous Pattern: \"" + c1 + c2 + c3 + "\"");
                    return resMap;
                }
            }
        }
        // Validation Complete
        resMap.put("res", true);
        resMap.put("msg", "Validation Complete");
        return resMap;

    }
}
