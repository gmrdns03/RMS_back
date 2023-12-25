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
public class SearchResListDto {
    private Integer contentId;
    private String contentNm;
    private String contentDesc;
    private String modfDt;
    private Integer boardId;
    private String boardNm;
    private Integer boardSn;
    private String cateNm;
    private String rentalStat;
    private String rentalStatNm;
    private String reservYn;
}
