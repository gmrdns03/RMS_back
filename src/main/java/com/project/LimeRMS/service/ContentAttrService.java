package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.mapper.ContentAttrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentAttrService {
    private final ContentAttrMapper contentAttrMapper;
    public String saveContentAttr(ContentAttrDto contentAttrDto){
        contentAttrMapper.insertContentAttr(contentAttrDto);
        return contentAttrDto.getLogicalAttr() + " 컨텐츠 속성이 생성되었습니다.";
    }

    public String deleteContentAttr(String loginUserId, String contentAttrId) {
        contentAttrMapper.deleteContentAttr(contentAttrId);
        return contentAttrId + " 컨텐츠 속성이 삭제되었습니다.";
    }

    public String updateContentAttr(String loginUserId, String contentAttrId, String logicalAttr, String physicalAttr, String attrOrder) {
        contentAttrMapper.updateContentAttr(contentAttrId, logicalAttr, physicalAttr, attrOrder, loginUserId);
        return contentAttrId + " 컨텐츠 속성이 수정되었습니다.";
    }
}
