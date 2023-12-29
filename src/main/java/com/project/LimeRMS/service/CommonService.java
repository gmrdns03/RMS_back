package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.CateHigherachyDto;
import com.project.LimeRMS.mapper.CommonMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final CommonMapper commonMapper;

    public Map<String, Object> getContentHigherachy(Integer cateId) {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("smallCateNm", null);
        resMap.put("middleCateNm", null);
        resMap.put("majorCateNm", null);
        resMap.put("smallCateId", null);
        resMap.put("middleCateId", null);
        resMap.put("majorCateId", null);
        List<CateHigherachyDto> cateHigherachyDto = commonMapper.findContentCateHigerachyByCateId(cateId);
        for (CateHigherachyDto dto : cateHigherachyDto) {
            Integer depth = dto.getDepth();
            if (depth == 0) {
                resMap.put("smallCateNm", dto.getCateNm());
                resMap.put("smallCateId", dto.getCateId());
            } else if (depth == 1) {
                resMap.put("middleCateNm", dto.getCateNm());
                resMap.put("middleCateId", dto.getCateId());
            } else if (depth == 2) {
                resMap.put("majorCateNm", dto.getCateNm());
                resMap.put("majorCateId", dto.getCateId());
            }
        }
        return resMap;
    }

    public String getContentHigherachyString(Integer cateId) {
        String smallCate = "";
        String middleCate = "";
        String majorCate = "";
        List<CateHigherachyDto> cateHigherachyDto = commonMapper.findContentCateHigerachyByCateId(cateId);
        for (CateHigherachyDto dto : cateHigherachyDto) {
            Integer depth = dto.getDepth();
            if (depth == 0) {
                smallCate = dto.getCateNm();
            } else if (depth == 1) {
                middleCate = dto.getCateNm();
            } else if (depth == 2) {
                majorCate = dto.getCateNm();
            }
        }
        if (middleCate.isEmpty() && majorCate.isEmpty()) {
            return smallCate;
        } else if (majorCate.isEmpty()) {
            return middleCate + " > " + smallCate;
        } else {
            return majorCate + " > " + middleCate + " > " + smallCate;
        }
    }
}
