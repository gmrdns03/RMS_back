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
    private final UserService userService;

    public BoardListDto getBoardListDto(Board board) {
        String boardStat = board.getBoardStat();
        Integer boardId = board.getBoardId();
        String boardTypeNm = board.getBoardType().getBoardTypeNm();
        String boardViewType = board.getBoardType().getBoardViewType();
        String contentViewType = board.getBoardType().getContentViewType();
        String boardNm = board.getBoardNm();
        String boardDesc = board.getBoardDesc();
        Integer boardSn = board.getBoardSn();
        Integer viewAuth = board.getViewAuth();
        Integer writeAuth = board.getWriteAuth();
        Integer commentAuth = board.getCommentAuth();
        Integer modifyAuth = board.getModifyAuth();
        Integer rentalPeriod = board.getRentalPeriod();
        Integer extensionLimit = board.getExtensionLimit();
        Integer rentalLimit = board.getRentalLimit();
        return new BoardListDto(boardId, boardTypeNm, boardViewType, contentViewType, boardNm, boardStat,
            boardDesc, boardSn, viewAuth, writeAuth, commentAuth, modifyAuth, rentalPeriod, extensionLimit, rentalLimit);
    }

    public BoardListDto getBoardInfo(Integer loginUserId, String boardId) throws Exception {
        Integer userAuthPriority = userService.getUserAuthPriority(String.valueOf(loginUserId));
        Board board = boardMapper.findOneByBoardId(boardId);
        Integer boardViewAuth = board.getViewAuth();
        if (userAuthPriority > boardViewAuth) {
            throw new IllegalAccessException("해당 게시판에 대한 접근 권한이 없습니다.");
        }
        return getBoardListDto(board);
    }

    public List<BoardListDto> findAllBoardList(String userId) {
        // 유저의 권한 확인
        Integer userAuthPriority = userService.getUserAuthPriority(userId);
        List<Board> boardList = boardMapper.findAllBoardList(userAuthPriority);
        List<BoardListDto> boardListDtoList = new ArrayList<>();

        for (Board board : boardList) {
            BoardListDto boardListDto = getBoardListDto(board);
            String boardStat =  boardListDto.getBoardStat();
            if (boardStat.equals("CD003002")) {
                continue;
            }
            boardListDtoList.add(boardListDto);
        }

        return boardListDtoList;
    }

    public File getBoardImg(String boardId) {
        // Board에서 boardId에 해당하는 boardImg 조회
        Board board = boardMapper.findOneByBoardId(boardId);
        String boardImgPath = board.getBoardImg();

        // 없는 경우 null 반환
        if (boardImgPath == null || boardImgPath.isEmpty()) { return null; }

        // 경로가 있는 경우 파일 불러오기
        return new File(boardImgPath);
    }

}
