package com.project.LimeRMS.dto;

import jakarta.persistence.criteria.CriteriaBuilder.In;
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
    private String boardTypeNm;
    private String boardNm;
    private String boardStat;
    private String boardDesc;
    private Integer boardSn;
    private Integer viewAuth;
    private Integer writeAuth;
    private Integer commentAuth;
    private Integer modifyAuth;
}
