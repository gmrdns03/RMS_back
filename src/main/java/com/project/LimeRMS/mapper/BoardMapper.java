package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.BoardInfoDto;
import com.project.LimeRMS.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardInfoDto> findAllBoard();
}
