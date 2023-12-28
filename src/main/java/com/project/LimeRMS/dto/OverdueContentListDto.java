package com.project.LimeRMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OverdueContentListDto {

    private Integer rentalId;

    private Integer userId;

    private String userNm;

    private Integer contentId;

    private String contentNm;

    private LocalDateTime rentalDt;

    private LocalDateTime predReturnDt;

    private String rentalStat;

    private String rentalStatNm;
}
