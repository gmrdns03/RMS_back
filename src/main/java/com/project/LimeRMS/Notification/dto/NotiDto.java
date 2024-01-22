package com.project.LimeRMS.Notification.dto;

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
public class NotiDto {

    private Integer notiId;
    private String notiContent;
    private String joinDt;

}
