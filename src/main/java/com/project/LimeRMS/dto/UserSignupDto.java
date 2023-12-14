package com.project.LimeRMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDto {
    @NotBlank(message = "사용자 이메일을 입력해주세요")
    @Size(min = 15, message = "올바른 이메일 주소를 입력해주세요")
    private String userEmail;

    @NotBlank(message = "사용자 이름을 입력해주세요")
    private String userNm;

    private String password;

    private String phoneNumber;

}
