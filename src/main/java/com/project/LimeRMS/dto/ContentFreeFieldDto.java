package com.project.LimeRMS.dto;

import java.util.Date;
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
public class ContentFreeFieldDto {
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private Integer integer1;
    private Integer integer2;
    private Integer integer3;
    private Integer integer4;
    private Integer integer5;
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    private Date date5;
    private Boolean bool1;
    private Boolean bool2;
    private Boolean bool3;
    private Boolean bool4;
    private Boolean bool5;
    private Float float1;
    private Float float2;
}
