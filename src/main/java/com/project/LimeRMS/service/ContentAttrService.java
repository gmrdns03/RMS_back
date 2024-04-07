package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.ContentAttrMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentAttrService {
    private final ContentAttrMapper contentAttrMapper;
    public String saveContentAttr(String loginUserId, Map<String, String> ContentAttrInfo){
        String contentAttrId = ContentAttrInfo.get("contentAttrId");
        Integer boardId = Integer.valueOf((ContentAttrInfo.get("boardId")));
        String logicalAttr = ContentAttrInfo.get("logicalAttr");
        String physicalAttr = ContentAttrInfo.get("physicalAttr");
        String mustYn = ContentAttrInfo.get("mustYn");
        Integer attrOrder = Integer.valueOf(ContentAttrInfo.get("attrOrder"));
        String attrType = ContentAttrInfo.get("attrType");

        contentAttrMapper.insertContentAttr(contentAttrId, boardId, logicalAttr, physicalAttr, mustYn, attrOrder, attrType, loginUserId);
        return logicalAttr + " 컨텐츠 속성이 생성되었습니다.";
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
