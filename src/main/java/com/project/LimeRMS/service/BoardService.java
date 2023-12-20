package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardInfoDto;
import com.project.LimeRMS.entity.Board;
import com.project.LimeRMS.mapper.BoardMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public List<BoardInfoDto> findAllBoardInfo() {
        List<Board> boardList = boardMapper.findAllBoardInfo();
        List<BoardInfoDto> boardInfoDtoList = new ArrayList<>();

        for (Board board : boardList) {
            String boardStat = board.getBoardStat();
            if (boardStat.equals("CD003002")) {
                continue;
            }
            Integer boardId = board.getBoardId();
            String boardTypeNm = board.getBoardType().getBoardTypeNm();
            String boardNm = board.getBoardNm();
            String boardDesc = board.getBoardDesc();
            Integer boardSn = board.getBoardSn();
            Integer viewAuth = board.getViewAuth();
            Integer writeAuth = board.getWriteAuth();
            Integer commentAuth = board.getCommentAuth();
            Integer modifyAuth = board.getModifyAuth();
            BoardInfoDto boardInfoDto = new BoardInfoDto(boardId, boardTypeNm, boardNm, boardStat, boardDesc, boardSn, viewAuth, writeAuth, commentAuth, modifyAuth);
            boardInfoDtoList.add(boardInfoDto);
        }
        return boardInfoDtoList;
    }

    public File getBoardImg(String boardId) {
        // Board에서
    }

}
