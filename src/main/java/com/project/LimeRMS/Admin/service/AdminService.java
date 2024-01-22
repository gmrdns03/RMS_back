package com.project.LimeRMS.Admin.service;

import com.project.LimeRMS.Admin.dto.AuthListDto;
import com.project.LimeRMS.Admin.mapper.AuthenticationMapper;
import com.project.LimeRMS.Board.dto.BoardInfoDto;
import com.project.LimeRMS.Admin.dto.BoardPriorityDto;
import com.project.LimeRMS.Admin.dto.ContentListDto;
import com.project.LimeRMS.Admin.dto.UserInfoDto;
import com.project.LimeRMS.Board.mapper.BoardMapper;
import com.project.LimeRMS.Common.mapper.CommCdMapper;
import com.project.LimeRMS.Notification.mapper.NotificationMapper;
import com.project.LimeRMS.Rental.mapper.RentalMapper;
import com.project.LimeRMS.User.mapper.UserMapper;
import com.project.LimeRMS.entity.Notification;
import com.project.LimeRMS.entity.User;
import java.io.File;
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
    private final NotificationMapper notificationMapper;
    private final PasswordEncoder passwordEncoder;
    private final CommCdMapper commCdMapper;

    //모든 회원의 정보 불러오기
    public List<UserInfoDto> getAllUserInformation() {

        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<User> users = userMapper.findAllUser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (User user : users) {
            Integer userId = user.getUserId();
            String userNm = user.getUserNm();
            String userEmail = user.getUserEmail();
            String joinDt = formatter.format(user.getJoinDt());
            String authNm = user.getAuthentication().getAuthNm();
            String userStat = user.getUserStat();
            String userStatNm = commCdMapper.findCdNmByCd(userStat);
            String phoneNumber = user.getPhoneNumber();
            UserInfoDto userInfoDto = new UserInfoDto(userId, userNm, userEmail, joinDt, authNm, userStat, userStatNm, phoneNumber);

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

    //유저 프로필 이미지 삭제
    public String deleteUserProfile(Integer managerId, Map<String, Integer> member){
        Integer userId = member.get("userId");
        String profileImg = userMapper.findProfileImgByUserId(userId);
        File file = new File(profileImg);
        profileImg = "";
        if (file.exists()) {
            file.delete();
            userMapper.updateProfileImgByManagerId(managerId, userId, profileImg);
            return "프로필 이미지 삭제 성공";
        }
        return "파일이 존재하지 않습니다.";
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
            //Cd -> Nm 찾기
            String boardStatNm = commCdMapper.findCdNmByCd(board.getBoardStat());
            board.setBoardStatNm(boardStatNm);
        }
        return boardInfoDtoList;
    }

    public List<ContentListDto> getOverdueContentList() {
        List<ContentListDto> overdueContents = rentalMapper.findRentalByRentalStat("CD001003"); //상태: 연체
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        for (ContentListDto overdueContent : overdueContents){
            //rentalDt 포맷 변경
            LocalDateTime rentalDt = LocalDateTime.parse(overdueContent.getPredReturnDt(), inputFormatter);
            String formattedRentalDt = rentalDt.format(outputFormatter);
            overdueContent.setRentalDt(formattedRentalDt);
            //predReturnDt 포맷 변경
            LocalDateTime predReturnDt = LocalDateTime.parse(overdueContent.getPredReturnDt(), inputFormatter);
            String formattedPredReturnDt = predReturnDt.format(outputFormatter);
            overdueContent.setPredReturnDt(formattedPredReturnDt);
            //Cd -> Nm 찾기
            String rentalStatNm = commCdMapper.findCdNmByCd(overdueContent.getRentalStat());
            overdueContent.setRentalStatNm(rentalStatNm);
        }
        return overdueContents;
    }

    public String changeContentRentalStat(String modfUserId, Map<String, Integer> member){
        Integer rentalUserId = member.get("rentalUserId");
        Integer contentId = member.get("contentId");
        //rentalUserId, contentId가 같은 컨텐츠를 찾아 rentalStat = "CD001002" 반납 -> "CD001001" 대여 상태로 변경
        rentalMapper.findRentalByContentId(rentalUserId, contentId, modfUserId);
        return "컨텐츠의 상태가 반납에서 대여로 변경되었습니다.";
    }

    public String addReReturnNotification(String regUserId, Map<String, Integer> member){

        Integer receiverId = member.get("userId");
        Integer contentId = member.get("contentId");
        //유저가 잘못 반납한 컨텐츠의 대여 정보 불러오기 (관리자가 다시 미반납처리 했다는 가정)
        ContentListDto reReturnRental = rentalMapper.findCanceledRentalByUserId(receiverId, contentId);
        String notiType = "CD004005"; //반납취소
        String contentNm = reReturnRental.getContentNm();
        String notiContent = "컨텐츠 '"+contentNm+"'의 반납이 확인되지 않아 미반납처리 되었습니다. 해당 컨텐츠가 정상적으로 반납되었는지 다시 확인부탁드립니다.";

        //이 contentId로 된 나에게 온 notiType이 반납취소인 오늘 생성된 알람이 있는가?
        Notification noti = notificationMapper.findNotiByContentId(contentId, notiType, receiverId);
        if (noti != null){
            notificationMapper.insertOverdueNoti(contentId, receiverId, Integer.valueOf(regUserId), notiType, notiContent);
            Integer notiId = noti.getNotiId();
            String notiStatus = noti.getNotiStatus();
            //미확인("CD005001")이라면 전 일자의 알림은 비활성화
            if (Objects.equals(notiStatus, "CD005001")){
                notificationMapper.updateNotiStatusByNotiId(notiId);
            }
        } else { //해당 알림이 없다면 추가
            notificationMapper.insertOverdueNoti(contentId, receiverId, Integer.valueOf(regUserId), notiType, notiContent);
        }
        return "대여 상태로 변경된 반납 컨텐츠가 성공적으로 알림에 추가되었습니다.";
    }

    public String changeBoardPriorities(String managerId, List<BoardPriorityDto> boardPriorityDtoList){
        for (BoardPriorityDto boardPriorityDto : boardPriorityDtoList){
            Integer boardSn = boardPriorityDto.getBoardSn();
            Integer boardId = boardPriorityDto.getBoardId();
            boardMapper.updateBoardSnByBoardId(boardSn, boardId, managerId);
        }
        return "보드의 우선순위가 변경되었습니다.";
    }
}
