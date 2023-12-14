package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.dto.UserSignupDto;
import com.project.LimeRMS.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "UserController API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtResponseDto jwtResponseDto;

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "사용자의 이메일과 비밀번호를 사용하여 로그인하고 토큰을 발급해준다.")
    public JwtResponseDto login(
            @Parameter(name = "userEmail", description = "사용자 이메일", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "userEmail") String userEmail,
            @Parameter(name = "password", description = "사용자 비밀번호", in = ParameterIn.QUERY, required = true)
            @RequestParam(name = "password") String password
    ) {
        return userService.login(userEmail, password);
    }

    //Test 코드(나중에 연관 코드 삭제 필요) - db에 저장된 비밀번호 BCryptPasswordEncoder로 변경해줌
    @PostMapping("/updatePassword")
    public void updatePassword(@Parameter(name = "userEmail", description = "사용자 이메일", in = ParameterIn.QUERY, required = true)
                                   @RequestParam(name="userEmail") String userEmail) {
        userService.updatePassword(userEmail);
    }

    @PostMapping("/signup")
    @Operation(summary = "사용자 등록")
//    public Integer signup(
//        @Parameter(name = "userEmail", description = "사용자 이메일", in = ParameterIn.QUERY, required = true)
//        @RequestParam(name = "userEmail") String userEmail,
//        @Parameter(name = "userNm", description = "사용자 이메일", in = ParameterIn.QUERY, required = true)
//        @RequestParam(name = "userNm") String userNm,
//        @Parameter(name = "phoneNumber", description = "사용자 전화번호", in = ParameterIn.QUERY)
//        @RequestParam(name = "phoneNumber") String phoneNumber
//    ) {
    public Integer signup(
        UserSignupDto userSignupDto
    ) {
        return userService.signup(userSignupDto);
    }

}
