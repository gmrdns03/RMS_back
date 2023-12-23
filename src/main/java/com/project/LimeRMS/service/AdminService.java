package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.AuthListDto;
import com.project.LimeRMS.dto.BoardInfoDto;
import com.project.LimeRMS.dto.OverdueContentListDto;
import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdminService.class.getName());

    private final UserMapper userMapper;
    private final BoardMapper boardMapper;
    private final RentalMapper rentalMapper;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final CommCdMapper commCdMapper;

    //모든 회원의 정보 불러오기
    public List<UserInfoDto> getUserInformation() {

        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<User> users = userMapper.findAllUser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (User user : users) {
            Integer userId = user.getUserId();
            String userNm = user.getUserNm();
            String userEmail = user.getUserEmail();
            String joinDt = formatter.format(user.getJoinDt());
            String authNm = user.getAuthentication().getAuthNm();
            String userStat = commCdMapper.findCdNmByCd(user.getUserStat());
            String phoneNumber = user.getPhoneNumber();
            UserInfoDto userInfoDto = new UserInfoDto(userId, userNm, userEmail, joinDt, authNm, userStat, phoneNumber);

            userInfoDtoList.add(userInfoDto);
        }
        return userInfoDtoList;
    }

    //관리자의 회원 추가 기능
    public String addUser(String managerId, Map<String, String> signupInfo) {
        if (userMapper.findByUserEmail(signupInfo.get("userEmail")).orElse(null) != null) {
            return signupInfo.get("userEmail") + "은 이미 가입된 Email 입니다.";
        } else {
            String userEmail = signupInfo.get("userEmail");
            String userNm = signupInfo.get("userNm");
            String phoneNumber = signupInfo.get("phoneNumber");
            String defaultPassword = System.getenv("DEFAULT_PW"); //초기 비밀번호 환경변수에 저장 됨
            String password = passwordEncoder.encode(defaultPassword);
            Integer authId = Integer.valueOf(signupInfo.get("authId"));
            Integer regUserId = Integer.valueOf(managerId);

            userMapper.addUser(userEmail, userNm, password, phoneNumber, authId, regUserId);
            return userEmail + "이 성공적으로 가입되었습니다.";
        }
    }

    //유저 프로필 정보 변경
    public String updateUserProfile(String managerId, Map<String, String> member) {
        Integer userId = Integer.valueOf(member.get("userId"));
        String userNm = member.get("userNm");
        Integer authId = Integer.valueOf(member.get("authId"));
        String userStat = member.get("userStat");
        String phoneNumber = member.get("phoneNumber");
        Integer modfUserId = Integer.valueOf(managerId);

        userMapper.updateUserByUserId(userId, userNm, authId, userStat, phoneNumber, modfUserId);
        return userNm + "님의 프로필 정보가 변경되었습니다.";
    }

    //유저 비밀번호를 지정된 값으로 초기화
    public String resetUserPw(String managerId, Map<String, Integer> member){
        Integer userId = member.get("userId");
        String defaultPassword = System.getenv("DEFAULT_PW");
        String password = passwordEncoder.encode(defaultPassword);
        Integer modfUserId = Integer.valueOf(managerId);

        userMapper.updatePwByUserId(userId, password, modfUserId);
        return "비밀번호가 초기화 되었습니다.";
    }

    //권한 종류 불러오기
    public List<AuthListDto> getAuthenticationList(){
        return authenticationMapper.findAllAuth();
    }

    //모든 보드 종류 불러오기
    public List<BoardInfoDto> getBoardList(){
        List<BoardInfoDto> boardInfoDtoList = boardMapper.findAllBoardInfoList();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (BoardInfoDto board : boardInfoDtoList){
            //regDt 포맷 변경
            LocalDateTime regDt = LocalDateTime.parse(board.getRegDt(), inputFormatter);
            String formattedRegDt = regDt.format(outputFormatter);
            board.setRegDt(formattedRegDt);
            //Cd를 Nm으로 변경
            String boardStat = commCdMapper.findCdNmByCd(board.getBoardStat());
            board.setBoardStat(boardStat);
        }
        return boardInfoDtoList;
    }

    public List<OverdueContentListDto> getOverdueContentList() {
        List<OverdueContentListDto> overdueContentListDtos = rentalMapper.findRentalByRentalStat("CD001003"); //상태: 연체
        return overdueContentListDtos;
    }
}
