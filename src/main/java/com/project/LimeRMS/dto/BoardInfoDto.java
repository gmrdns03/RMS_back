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
public class BoardInfoDto {

    private Integer boardId;

    private String boardNm;

    private String regDt;

    private String contentNum;

    private String boardStat;

    private String boardStatNm;

    private Integer boardSn;
}
