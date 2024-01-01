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
    private Integer boardId;
    private String contentNm;
    private String contentDesc;
    private String contentHtml;
    private String noticeYn;
    private String secretYn;
    private String location;
    private String rentalMessage;
    private String regDt;
    private Integer regUserId;
    private String modfDt;
    private Integer modfUserId;

    // 컨텐츠 대여 상태
    private String rentalStat;
    private String rentalStatNm;

    // 컨텐츠 예약 상태
    private String reservedYn;

    // 컨텐츠 좋아요 개수
    private Integer likeCnt;

    // 컨텐츠 카테고리
    private String smallCateNm;
    private String middleCateNm;
    private String majorCateNm;
    private Integer smallCateId;
    private Integer middleCateId;
    private Integer majorCateId;
}
