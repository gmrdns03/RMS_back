package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.entity.Board;
import com.project.LimeRMS.mapper.BoardMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final UserService userService;

    @Value("${filesPath.board}")
    private String DtlBoardPath;

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

    public File getBoardImg(String boardId) throws FileNotFoundException {
        // Board에서 boardId에 해당하는 boardImg 조회
        Board board = boardMapper.findOneByBoardId(boardId);
        String boardImgPath = board.getBoardImg();

        // 없는 경우 null 반환
        if (boardImgPath == null || boardImgPath.isEmpty()) {
            throw new FileNotFoundException("there is no board image");
        }

        // 경로가 있는 경우 파일 불러오기
        File destFile = new File(boardImgPath);
        if (destFile.exists()) {
            return destFile;
        } else {
            throw  new FileNotFoundException("cannot be resolved in the file system for checking its content length");
        }
    }

    public void saveBoardImg(String loginUserId, Integer boardId, MultipartFile multipartFile) throws Exception {
        // 로그인 유저의 권한 확인
        Integer loginUserAuthPriority = userService.getUserAuthPriority(loginUserId);
        if (loginUserAuthPriority > 4) {
            throw new IllegalAccessException("게시판 대표 이미지 변경 권한이 없습니다.");
        }

        // 보드 관리자 권한 확인
        List<String> boardMngList = boardMapper.findAllBoardManagerByBoardId(boardId);
        if (!boardMngList.contains(loginUserId)) {
            throw new IllegalAccessException("게시판 대표 이미지 변경 권한이 없습니다.");
        }

        String boardFolderPath = System.getProperty("user.dir") + DtlBoardPath;
        // multipartFile이 비어 있지 않으면 진행
        if (multipartFile != null && !multipartFile.isEmpty()) {
            // board 폴더가 없는 경우 폴더 생성
            Path folderPath = Paths.get(boardFolderPath);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }
        } else {
            throw new FileNotFoundException("Not enough elements. Plz check your data");
        }

        // 보내온 파일 저장
        UUID uuid = UUID.randomUUID();
        String fileNm = uuid + "_" + multipartFile.getOriginalFilename();
        File file = new File(boardFolderPath, fileNm);
        multipartFile.transferTo(file);

        // 기존 boardImg 가 있는지 확인 -> 이미 있는 경우 해당 파일 삭제
        String boardImgPath = boardMapper.findBoardImgPathByBoardId(boardId);
        if ((boardImgPath != null) && !boardImgPath.isEmpty()) {
            deleteFile(boardImgPath);
        }

        // boardImgPath 테이블에 업데이트
        String newBoardImgPath = boardFolderPath + "/" + fileNm;
        boardMapper.updateBoardImgByBoardId(boardId, newBoardImgPath, loginUserId);
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {file.delete();}
    }
}
