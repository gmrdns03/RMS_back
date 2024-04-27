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
public class CommentDto {

    private Integer commentId;

    private Integer parentCommentId;

    private String comment;

    private Integer score;

    private Integer userId;

    private String userNm;

    private String profileImg;

    private String modfDt;

    private String delYn;

}
