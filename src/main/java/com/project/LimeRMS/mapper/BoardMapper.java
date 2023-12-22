package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.project.LimeRMS.dto.BoardInfoDto;

@Mapper
public interface BoardMapper {
    List<Board> findAllBoardList();
    Board findByBoardId(@Param("boardId") String boardId);
    List<BoardInfoDto> findAllBoard();
}
