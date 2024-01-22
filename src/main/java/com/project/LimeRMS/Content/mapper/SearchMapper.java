package com.project.LimeRMS.Content.mapper;

import com.project.LimeRMS.Content.dto.SearchResListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SearchMapper {
    List<SearchResListDto> findAllByKeyword(@Param("keyword") String keyword, @Param("priority") Integer priority);
    List<SearchResListDto> findAllBykeywordBoardId(@Param("keyword") String keyword, @Param("priority") Integer priority, @Param("boardId") Integer boardId);
}
