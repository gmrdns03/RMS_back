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
public class RentalListDto {
    private Integer rentalId;
    private String boardNm;
    private Integer boardId;
    private String cateNm;
    private Integer contentId;
    private String contentNm;
    private String rentalDt;
    private String predReturnDt;
    private String returnDt;
    private String rentalStat;
    private String rentalStatNm;
}
