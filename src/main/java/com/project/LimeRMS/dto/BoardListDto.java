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
public class BoardListDto {
    private Integer boardId;
    private String boardTypeNm;
    private String boardViewType;
    private String contentViewType;
    private String boardNm;
    private String boardStat;
    private String boardDesc;
    private Integer boardSn;
    private Integer viewAuth;
    private Integer writeAuth;
    private Integer commentAuth;
    private Integer modifyAuth;
    private Integer rentalPeriod;
    private Integer extensionLimit;
    private Integer rentalLimit;
}
