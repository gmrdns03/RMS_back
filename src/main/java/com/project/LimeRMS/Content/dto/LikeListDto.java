package com.project.LimeRMS.Content.dto;

import java.time.LocalDateTime;
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
public class LikeListDto {
    private Integer likeId;
    private String boardNm;
    private Integer boardId;
    private String cateNm;
    private Integer contentId;
    private String contentNm;
    private String regDt;
    private String rentalStat;
    private String rentalStatNm;
}
