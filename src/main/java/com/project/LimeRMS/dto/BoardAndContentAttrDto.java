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
    private ContentAttrOrder contentAttrOrder;

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
        private String integer1;
        private String integer2;
        private String integer3;
        private String integer4;
        private String integer5;
        private String date1;
        private String date2;
        private String date3;
        private String date4;
        private String date5;
        private String bool1;
        private String bool2;
        private String bool3;
        private String bool4;
        private String bool5;
        private String float1;
        private String float2;
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
        private String integer1;
        private String integer2;
        private String integer3;
        private String integer4;
        private String integer5;
        private String date1;
        private String date2;
        private String date3;
        private String date4;
        private String date5;
        private String bool1;
        private String bool2;
        private String bool3;
        private String bool4;
        private String bool5;
        private String float1;
        private String float2;
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
        private String integer1;
        private String integer2;
        private String integer3;
        private String integer4;
        private String integer5;
        private String date1;
        private String date2;
        private String date3;
        private String date4;
        private String date5;
        private String bool1;
        private String bool2;
        private String bool3;
        private String bool4;
        private String bool5;
        private String float1;
        private String float2;
    }

    @Builder
    @Data
    @Component
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContentAttrOrder{
        private Integer text1;
        private Integer text2;
        private Integer text3;
        private Integer text4;
        private Integer text5;
        private Integer integer1;
        private Integer integer2;
        private Integer integer3;
        private Integer integer4;
        private Integer integer5;
        private Integer date1;
        private Integer date2;
        private Integer date3;
        private Integer date4;
        private Integer date5;
        private Integer bool1;
        private Integer bool2;
        private Integer bool3;
        private Integer bool4;
        private Integer bool5;
        private Integer float1;
        private Integer float2;
    }
}
