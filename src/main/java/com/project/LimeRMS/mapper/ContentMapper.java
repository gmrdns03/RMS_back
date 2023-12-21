package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.ContentInfoDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContentMapper {
    List<ContentInfoDto> findContentsByBoardId(@Param("boardId") String boardId);
}
