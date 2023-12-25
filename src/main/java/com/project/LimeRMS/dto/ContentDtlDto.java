package com.project.LimeRMS.dto;

import java.util.Date;
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
public class ContentDtlDto {
    private Integer contentId;
    private Integer cateId;
    private String cateNm;
    private Integer boardId;
    private String contentNm;
    private String contentDesc;
    private String contentImg;
    private String contentHtml;
    private String delYn;
    private String noticeYn;
    private String secretYn;
    private String location;
    private String rentalMessage;
    private String regDt;
    private Integer regUserId;
    private String modfDt;
    private Integer modfUserId;
}
