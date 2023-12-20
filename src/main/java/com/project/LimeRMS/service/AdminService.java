package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.AuthListDto;
import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.AuthenticationMapper;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminService.class.getName());

    private final UserMapper userMapper;

    private final AuthenticationMapper authenticationMapper;

    private final PasswordEncoder passwordEncoder;

    private final CommCdMapper commCdMapper;

    public List<UserInfoDto> getUserInformation() {

        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<User> users = userMapper.findAllUser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (User user : users) {
            String userNm = user.getUserNm();
            String userEmail = user.getUserEmail();
            String joinDt = formatter.format(user.getJoinDt());
            Integer authId = userMapper.findAuthIdByUserId(user.getUserId());
            String authNm = authenticationMapper.findAuthNmByAuthId(authId);
            String userStat = commCdMapper.findCdNmByUserStat(user.getUserStat());
            UserInfoDto userInfoDto = new UserInfoDto(userNm, userEmail, joinDt, authNm, userStat);

            userInfoDtoList.add(userInfoDto);
        }
        return userInfoDtoList;
    }

    public String addUser(Map<String, String> signupInfo) {
        try {
            if (userMapper.findByUserEmail(signupInfo.get("userEmail")).orElse(null) != null) {
                throw new RuntimeException("이미 가입된 Email 입니다.");
            }
            String userEmail = signupInfo.get("userEmail");
            String userNm = signupInfo.get("userNm");
            String phoneNumber = signupInfo.get("phoneNumber");
            String defaultPassword = System.getenv("DEFAULT_PW"); //초기 비밀번호 환경변수에 저장 됨
            String password = passwordEncoder.encode(defaultPassword);
            Integer authId = Integer.valueOf(signupInfo.get("authId"));

            userMapper.addUser(userEmail, userNm, password, phoneNumber, authId);
            return userEmail + "이 성공적으로 가입되었습니다.";
        } catch (Exception e) {
            return e.toString();
        }
    }

    //유저 개인정보 수정
    public String updateUserInfo(Map<String, String> updateInfo){

    }

    //권한 종류 불러오기
    public List<AuthListDto> getAuthenticationList(){
        try {
            return authenticationMapper.findAllAuth();
        } catch (Exception e) {
            log.info("Error while getting authentication list", e);
            return Collections.emptyList();
        }
    }
}
