package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.CommCdDto;
import com.project.LimeRMS.dto.ContentAttrDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommCdMapper {
    String findCdNmByCd(String commCd);

    List<CommCdDto> findCommCdByHiCommCd(String hiCommCd);

    List<ContentAttrDto> findContentAttrByBoardId(@Param("boardId") Integer boardId);
}
