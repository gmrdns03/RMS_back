package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.JwtResponseDto;
import com.project.LimeRMS.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "UserController", description = "UserController API")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtResponseDto jwtResponseDto;

    @PostMapping("/login")
    @Operation(
            summary = "사용자 로그인",
            description = "사용자의 이메일과 비밀번호를 사용하여 로그인하고 토큰을 발급해준다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"userEmail\":\"test1@euclidsoft.co.kr\",\"password\":\"1234\"}")
                    )
            )
    )
    public JwtResponseDto login(@RequestBody Map<String, String> member) {
        return userService.login(member);
    }

    //Test 코드(나중에 연관 코드 삭제 필요) - db에 저장된 비밀번호 BCryptPasswordEncoder로 변경해줌
    @PostMapping("/updatePassword")
    public void updatePassword(@Parameter(name = "userEmail", description = "사용자 이메일", in = ParameterIn.QUERY, required = true)
                                   @RequestParam(name="userEmail") String userEmail) {
        userService.updatePassword(userEmail);
    }

}
