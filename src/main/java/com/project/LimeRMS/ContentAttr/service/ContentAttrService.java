package com.project.LimeRMS.ContentAttr.service;

import com.project.LimeRMS.ContentAttr.mapper.ContentAttrMapper;
import com.project.LimeRMS.entity.Content;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentAttrService {
    private final ContentAttrMapper contentAttrMapper;
    public String saveContentAttr(String loginUserId, Map<String, String> ContentAttrInfo){
        Integer boardId = Integer.valueOf((ContentAttrInfo.get("boardId")));
        String logicalAttr = ContentAttrInfo.get("logicalAttr");
        String physicalAttr = ContentAttrInfo.get("physicalAttr");
        String mustYn = ContentAttrInfo.get("mustYn");
        Integer attrOrder = Integer.valueOf(ContentAttrInfo.get("attrOrder"));
        String attrType = ContentAttrInfo.get("attrType");

        contentAttrMapper.insertContentAttr(boardId, logicalAttr, physicalAttr, mustYn, attrOrder, attrType, loginUserId);
        return logicalAttr + " 컨텐츠 속성이 생성되었습니다.";
    }
}
