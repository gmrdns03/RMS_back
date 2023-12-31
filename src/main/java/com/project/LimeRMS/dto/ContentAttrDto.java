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
public class ContentAttrDto {
    private String logicalAttr;
    private String physicalAttr;
    private String attrType;
    private String mustYn;
    private String attrOrder;

}
