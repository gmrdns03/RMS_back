package com.project.LimeRMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {

    private String authNm;
    private String authDesc;
    private Integer priority;
    private String regUserId;
    private String modfUserId;

}
