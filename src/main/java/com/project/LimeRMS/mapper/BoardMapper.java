package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {
    List<Board> findAllBoardInfo();
    Board findByBoardId(@Param("boardId") String boardId);
}
