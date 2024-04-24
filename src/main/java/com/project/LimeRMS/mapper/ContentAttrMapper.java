package com.project.LimeRMS.mapper;


import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.entity.ContentAttr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentAttrMapper {
    void insertContentAttr(ContentAttrDto contentAttrDto);

    void deleteContentAttr(@Param("contentAttrId") String ContentAttrId);

    void updateContentAttr(@Param("contentAttrId") String contentAttrId, @Param("logicalAttr") String logicalAttr, @Param("physicalAttr") String physicalAttr, @Param("attrOrder") String attrOrder, @Param("modfUserId") String loginUserId);
}
