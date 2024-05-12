package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardTypeDto;
import com.project.LimeRMS.mapper.BoardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardTypeService {
    private final BoardTypeMapper boardTypeMapper;

    public void saveBoardType(String loginUserId, BoardTypeDto boardTypeDto){
        boardTypeMapper.insertBoardType(boardTypeDto);
    }

    public void deleteBoardType(String loginUserId, BoardTypeDto boardTypeDto){
        boardTypeMapper.deleteBoardType(boardTypeDto);
    }

    public BoardTypeDto getBoardType(BoardTypeDto boardTypeDto){
        return boardTypeMapper.getBoardType(boardTypeDto);
    }

    public List<BoardTypeDto> getBoardTypeList(){
        return boardTypeMapper.getBoardTypeList();
    }
}
