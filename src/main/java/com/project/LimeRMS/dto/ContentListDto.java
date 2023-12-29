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
public class ContentListDto {

    private Integer rentalId;

    private Integer userId;

    private String userNm;

    private Integer contentId;

    private String contentNm;

    private String rentalDt;

    private String predReturnDt;

    private String rentalStat;

    private String rentalStatNm;
}
