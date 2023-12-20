package com.project.LimeRMS.mapper;

import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    List<Board> findAllBoardInfo();
}
