package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.CommCdDto;
import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.UserMapper;
import com.project.LimeRMS.security.JwtProvider;

import java.util.ArrayList;
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
    private final JwtResponseDto jwtResponseDto;
    private final JwtProvider jwtProvider;

    public List<CommCdDto> getCommCdList(Map<String, String> input) {
        try {
            List<CommCdDto> commCdDtoList = commCdMapper.findCommCdByHiCommCd(input.get("hiCommCd"));
            return commCdDtoList;
        } catch (Exception e) {
            List<CommCdDto> errorList = new ArrayList<>();
            errorList.add(new CommCdDto("error", "에러 발생: " + e.getMessage()));
            return errorList;
        }
    }

    public JwtResponseDto login(Map<String, String> member) {
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

        String accessToken = jwtProvider.generateAccessToken(userNm, email, priority, authNm, userId);

        return JwtResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }

    //Test 코드(나중에 연관 코드 삭제 필요)
    public void updatePassword(String userEmail){
        User user = userMapper.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 Email 입니다."));
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        userMapper.updatePasswordByUserEmail(userEmail, hashedPassword);
    }

}
