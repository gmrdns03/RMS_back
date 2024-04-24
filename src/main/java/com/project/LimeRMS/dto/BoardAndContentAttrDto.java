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
public class BoardAndContentAttrDto {
    private Integer boardId;
    private Integer boardTypeId;
    private String boardNm;
    private String boardDesc;
    private String boardStat;
    private Integer boardSn;
    private Integer rentalPeriod;
    private Integer extensionLimit;
    private Integer rentalLimit;
    private Integer viewAuth;
    private Integer writeAuth;
    private Integer commentAuth;
    private Integer modifyAuth;
    private String scoreYn;
    private String commentYn;
    private Integer listNumLimit;
    private String delYn;
    private String commentImgYn;
    private String regUserId;
    private String modfUserId;
    private ContentAttrLogicalAttr contentAttrLogicalAttr;
    private ContentAttrType contentAttrType;
    private ContentAttrMustYn contentAttrMustYn;

    @Builder
    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContentAttrLogicalAttr{
        private String text1;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
    }

    @Builder
    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContentAttrType{
        private String text1;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
    }

    @Builder
    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContentAttrMustYn{
        private String text1;
        private String text2;
        private String text3;
        private String text4;
        private String text5;
    }
}
