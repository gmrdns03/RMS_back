package com.project.LimeRMS.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentAttrMapper {
    void insertContentAttr(@Param("contentAttrId") String contentAttrId, @Param("boardId") Integer boardId, @Param("logicalAttr") String logicalAttr, @Param("physicalAttr") String physicalAttr, @Param("mustYn") String mustYn, @Param("attrOrder") Integer attrOrder, @Param("attrType") String attrType, @Param("modfUserId") String modfUserId);

    void deleteContentAttr(@Param("contentAttrId") String ContentAttrId);
}
