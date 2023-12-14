package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.UserMapper;
import com.project.LimeRMS.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtResponseDto jwtResponseDto;
    private final JwtProvider jwtProvider;

    public JwtResponseDto login(String userEmail, String password) {
        User user = userMapper.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 Email 입니다."));
        String orgPassword = user.getPassword();

        if (!passwordEncoder.matches(password, orgPassword)) {
            System.out.println("Input Password: " + password);
            System.out.println("Stored Password: " + orgPassword);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createJwt(userEmail);

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
