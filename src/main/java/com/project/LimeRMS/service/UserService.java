package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.AuthListDto;
import com.project.LimeRMS.dto.CommCdDto;
import com.project.LimeRMS.dto.UserAuthPriorityDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.AuthenticationMapper;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.UserMapper;
import com.project.LimeRMS.security.JwtProvider;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final CommCdMapper commCdMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationMapper authenticationMapper;

    //유저 로그인시 토큰 발급
    public String login(Map<String, String> member) {
        String email = member.get("userEmail");
        String password = member.get("password");
        User user = userMapper.findByUserEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 Email 입니다."));
        String orgPassword = user.getPassword();

        if (!passwordEncoder.matches(password, orgPassword)) {
            System.out.println("Input Password: " + password);
            System.out.println("Stored Password: " + orgPassword);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        Integer priority = user.getAuthentication().getPriority();
        String authNm = user.getAuthentication().getAuthNm();
        String userNm = user.getUserNm();
        Integer userId = user.getUserId();

        return jwtProvider.generateAccessToken(userNm, email, priority, authNm, userId);
    }

    //상태값 종류 리스트로 확인 가능
    public List<CommCdDto> getCommCdList(Map<String, String> input) {
        return commCdMapper.findCommCdByHiCommCd(input.get("hiCommCd"));
    }

    // 유저 권한 priority 확인 기능
    public Integer getUserAuthPriority(String userId) {
        UserAuthPriorityDto authPriorityDto = authenticationMapper.findOneAuthByuserId(userId);
        return authPriorityDto.getPriority();
    }


}
