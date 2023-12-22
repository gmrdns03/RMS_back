package com.project.LimeRMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private Integer userId;

    private String userNm;

    private String userEmail;

    private String joinDt;

    private String authNm;

    private String userStat;

    private String userStatNm;

    private String phoneNumber;
}
