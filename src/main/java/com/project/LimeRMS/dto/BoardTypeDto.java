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
public class BoardTypeDto {
    private Integer boardTypeId;
    private String boardTypeNm;
    private String boardTypeDesc;
    private String contentViewType;
    private String boardViewType;
    private String regDt;
    private String regUserId;
    private String modfDt;
    private String modfUserId;
}
