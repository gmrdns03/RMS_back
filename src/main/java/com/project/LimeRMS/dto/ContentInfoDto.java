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
public class ContentInfoDto {
    private Integer contentId;
    private Integer cateId;
    private String cateNm;
    private String contentNm;
    private String contentDesc;
    private String noticeYn;
    private String secretYn;
    private String location;
    private String regDt;
    private String regUserId;
    private String modfDt;
    private String modfUserId;
}
