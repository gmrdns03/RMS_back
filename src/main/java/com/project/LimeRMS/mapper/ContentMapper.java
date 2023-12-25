package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.dto.ContentDtlDto;
import com.project.LimeRMS.entity.Content;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentMapper {
    List<Content> findContentsByBoardId(@Param("boardId") String boardId);

    List<ContentAttrDto> findContentAttrByBoardId(@Param("boardId")Integer boardId);

    ContentDtlDto findOneByContentId(@Param("contentId")Integer contentId);
}
