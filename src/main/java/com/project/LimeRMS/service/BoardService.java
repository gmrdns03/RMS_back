package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardListDto;
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

    public List<BoardListDto> findAllBoardList() {
        List<Board> boardList = boardMapper.findAllBoardList();
        List<BoardListDto> boardListDtoList = new ArrayList<>();

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
            BoardListDto boardListDto = new BoardListDto(boardId, boardTypeNm, boardNm, boardStat, boardDesc, boardSn, viewAuth, writeAuth, commentAuth, modifyAuth);
            boardListDtoList.add(boardListDto);
        }
        return boardListDtoList;
    }

    public File getBoardImg(String boardId) {
        // Board에서 boardId에 해당하는 boardImg 조회
        Board board = boardMapper.findByBoardId(boardId);
        String boardImgPath = board.getBoardImg();

        // 없는 경우 null 반환
        if (boardImgPath.isEmpty()) { return null; }

        // 경로가 있는 경우 파일 불러오기
        return new File(boardImgPath);
    }

}
