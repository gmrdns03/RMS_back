package com.project.LimeRMS.mapper;
import com.project.LimeRMS.dto.BoardTypeDto;
import com.project.LimeRMS.entity.BoardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardTypeMapper {
    void insertBoardType(BoardTypeDto boardTypeDto);

    void deleteBoardType(BoardTypeDto boardTypeDto);

    BoardTypeDto getBoardType(BoardTypeDto boardTypeDto);

    List<BoardTypeDto> getBoardTypeList();
}
