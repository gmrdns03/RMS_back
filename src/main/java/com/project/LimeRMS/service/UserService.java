package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.dto.UserSignupDto;
import com.project.LimeRMS.entity.User;
import com.project.LimeRMS.mapper.UserMapper;
import com.project.LimeRMS.security.JwtProvider;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtResponseDto jwtResponseDto;
    private final JwtProvider jwtProvider;

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

        String accessToken = jwtProvider.createJwt(email);

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

    // 사용자 등록
    @Transactional
    public int signup(UserSignupDto userSignupDto) {
        if (userMapper.findByUserEmail(userSignupDto.getUserEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입된 유저 입니다.");
        }

        // 초기 비밀번호 셋팅
        String password = passwordEncoder.encode("dbzmfflem1!");
        userSignupDto.setPassword(password);

        // 저장
        userMapper.signup(userSignupDto);

        // 검증
        Optional<User> user = userMapper.findByUserEmail(userSignupDto.getUserEmail());

        if (user.isPresent()) {
            return user.get().getUserId();
        } else {
            throw new RuntimeException("회원 등록에 실패했습니다.");
        }
    }
}
