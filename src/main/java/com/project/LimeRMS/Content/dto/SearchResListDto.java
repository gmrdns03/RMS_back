package com.project.LimeRMS.Content.dto;

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
public class SearchResListDto {
    private Integer contentId;
    private String contentNm;
    private String contentDesc;
    private String modfDt;
    private Integer boardId;
    private String boardNm;
    private Integer boardSn;
    private Integer cateId;
    private String rentalStat;
    private String rentalStatNm;
    private String reserveYn;
    private String smallCateNm;
    private String middleCateNm;
    private String majorCateNm;
    private Integer smallCateId;
    private Integer middleCateId;
    private Integer majorCateId;
}
