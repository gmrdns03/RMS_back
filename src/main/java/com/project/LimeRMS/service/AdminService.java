package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.entity.Authentication;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.AdminMapper;
import com.project.LimeRMS.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

    private final AuthenticationMapper authenticationMapper;

    public List<UserInfoDto> getUserInformation() {

        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<User> users = adminMapper.findAllUser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (User user : users) {
            Authentication authentication = user.getAuthentication();
            String userNm = user.getUserNm();
            String userEmail = user.getUserEmail();
            String joinDt = formatter.format(user.getJoinDt());
            Integer authId = adminMapper.findAuthIdByUserId(user.getUserId());
            String authNm = authenticationMapper.findAuthNmByAuthId(authId);
            String userStat = user.getUserStat();
            UserInfoDto userInfoDto = new UserInfoDto(userNm, userEmail, joinDt, authNm, userStat);

            userInfoDtoList.add(userInfoDto);
        }
        return userInfoDtoList;
    }
}
