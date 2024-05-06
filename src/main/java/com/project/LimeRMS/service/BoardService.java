package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardAndContentAttrDto;
import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.entity.Board;
import com.project.LimeRMS.mapper.BoardMapper;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.ContentAttrMapper;
import com.project.LimeRMS.mapper.ContentMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;
    private final UserService userService;
    private final CommCdMapper commCdMapper;
    private final ContentMapper contentMapper;
    private final ContentAttrMapper contentAttrMapper;

    @Value("${filesPath.board}")
    private String DtlBoardPath;

    public BoardListDto getBoardListDto(Board board) {
        String boardStat = board.getBoardStat();
        String boardStatNm = commCdMapper.findCdNmByCd(boardStat);
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
        return new BoardListDto(boardId, boardTypeNm, boardViewType, contentViewType, boardNm, boardStat, boardStatNm,
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
            String boardStat = boardListDto.getBoardStat();
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
            throw new FileNotFoundException("cannot be resolved in the file system for checking its content length");
        }
    }

    public void saveBoardImg(String loginUserId, Integer boardId, MultipartFile multipartFile) throws IllegalAccessException, IOException {
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
            throw new IOException("Not enough elements. Plz check your data");
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
        if (file.exists()) {
            file.delete();
        }
    }

    public Map<String, Object> getBoardFreeField(String boardId) {
        // 4. 보드속성 테이블에서 자유필드 컬럼 확인
        List<ContentAttrDto> contentAttrList = contentMapper.findContentAttrByBoardId(Integer.valueOf(boardId));
        Map<String, Object> freeFieldMap = new HashMap<>();
        for (ContentAttrDto dto : contentAttrList) {
            Map<String, Object> freeField = new HashMap<>();
            freeField.put("label", dto.getLogicalAttr());
            freeField.put("value", null);
            freeField.put("order", dto.getAttrOrder());
            String type = commCdMapper.findCdNmByCd(dto.getAttrType());
            freeField.put("type", type);
            freeField.put("required", dto.getMustYn());
            freeFieldMap.put(String.valueOf(dto.getAttrOrder()), freeField);
        }
        return freeFieldMap;
    }

    @Transactional
    public String saveBoard(String loginUserId, BoardAndContentAttrDto bcaDto) {
        bcaDto.setRegUserId(loginUserId);
        bcaDto.setModfUserId(loginUserId);
        boardMapper.insertBoard(bcaDto);
        if (bcaDto.getContentAttrLogicalAttr().getText1() != null && bcaDto.getContentAttrType().getText1() != null && bcaDto.getContentAttrMustYn().getText1() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "text1", bcaDto.getContentAttrLogicalAttr().getText1(), bcaDto.getContentAttrType().getText1(), bcaDto.getContentAttrMustYn().getText1(), bcaDto.getContentAttrOrder().getText1(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getText2() != null && bcaDto.getContentAttrType().getText2() != null && bcaDto.getContentAttrMustYn().getText2() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "text2", bcaDto.getContentAttrLogicalAttr().getText2(), bcaDto.getContentAttrType().getText2(), bcaDto.getContentAttrMustYn().getText2(), bcaDto.getContentAttrOrder().getText2(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getText3() != null && bcaDto.getContentAttrType().getText3() != null && bcaDto.getContentAttrMustYn().getText3() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "text3", bcaDto.getContentAttrLogicalAttr().getText3(), bcaDto.getContentAttrType().getText3(), bcaDto.getContentAttrMustYn().getText3(), bcaDto.getContentAttrOrder().getText3(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getText4() != null && bcaDto.getContentAttrType().getText4() != null && bcaDto.getContentAttrMustYn().getText4() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "text4", bcaDto.getContentAttrLogicalAttr().getText4(), bcaDto.getContentAttrType().getText4(), bcaDto.getContentAttrMustYn().getText4(), bcaDto.getContentAttrOrder().getText4(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getText5() != null && bcaDto.getContentAttrType().getText5() != null && bcaDto.getContentAttrMustYn().getText5() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "text5", bcaDto.getContentAttrLogicalAttr().getText5(), bcaDto.getContentAttrType().getText5(), bcaDto.getContentAttrMustYn().getText5(), bcaDto.getContentAttrOrder().getText5(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getInteger1() != null && bcaDto.getContentAttrType().getInteger1() != null && bcaDto.getContentAttrMustYn().getInteger1() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "integer1", bcaDto.getContentAttrLogicalAttr().getInteger1(), bcaDto.getContentAttrType().getInteger1(), bcaDto.getContentAttrMustYn().getInteger1(), bcaDto.getContentAttrOrder().getInteger1(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getInteger2() != null && bcaDto.getContentAttrType().getInteger2() != null && bcaDto.getContentAttrMustYn().getInteger2() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "integer2", bcaDto.getContentAttrLogicalAttr().getInteger2(), bcaDto.getContentAttrType().getInteger2(), bcaDto.getContentAttrMustYn().getInteger2(), bcaDto.getContentAttrOrder().getInteger2(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getInteger3() != null && bcaDto.getContentAttrType().getInteger3() != null && bcaDto.getContentAttrMustYn().getInteger3() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "integer3", bcaDto.getContentAttrLogicalAttr().getInteger3(), bcaDto.getContentAttrType().getInteger3(), bcaDto.getContentAttrMustYn().getInteger3(), bcaDto.getContentAttrOrder().getInteger3(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getInteger4() != null && bcaDto.getContentAttrType().getInteger4() != null && bcaDto.getContentAttrMustYn().getInteger4() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "integer4", bcaDto.getContentAttrLogicalAttr().getInteger4(), bcaDto.getContentAttrType().getInteger4(), bcaDto.getContentAttrMustYn().getInteger4(), bcaDto.getContentAttrOrder().getInteger4(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getInteger5() != null && bcaDto.getContentAttrType().getInteger5() != null && bcaDto.getContentAttrMustYn().getInteger5() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "integer5", bcaDto.getContentAttrLogicalAttr().getInteger5(), bcaDto.getContentAttrType().getInteger5(), bcaDto.getContentAttrMustYn().getInteger5(), bcaDto.getContentAttrOrder().getInteger5(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getDate1() != null && bcaDto.getContentAttrType().getDate1() != null && bcaDto.getContentAttrMustYn().getDate1() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "date1", bcaDto.getContentAttrLogicalAttr().getDate1(), bcaDto.getContentAttrType().getDate1(), bcaDto.getContentAttrMustYn().getDate1(), bcaDto.getContentAttrOrder().getDate1(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getDate2() != null && bcaDto.getContentAttrType().getDate2() != null && bcaDto.getContentAttrMustYn().getDate2() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "date2", bcaDto.getContentAttrLogicalAttr().getDate2(), bcaDto.getContentAttrType().getDate2(), bcaDto.getContentAttrMustYn().getDate2(), bcaDto.getContentAttrOrder().getDate2(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getDate3() != null && bcaDto.getContentAttrType().getDate3() != null && bcaDto.getContentAttrMustYn().getDate3() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "date3", bcaDto.getContentAttrLogicalAttr().getDate3(), bcaDto.getContentAttrType().getDate3(), bcaDto.getContentAttrMustYn().getDate3(), bcaDto.getContentAttrOrder().getDate3(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getDate4() != null && bcaDto.getContentAttrType().getDate4() != null && bcaDto.getContentAttrMustYn().getDate4() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "date4", bcaDto.getContentAttrLogicalAttr().getDate4(), bcaDto.getContentAttrType().getDate4(), bcaDto.getContentAttrMustYn().getDate4(), bcaDto.getContentAttrOrder().getDate4(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getDate5() != null && bcaDto.getContentAttrType().getDate5() != null && bcaDto.getContentAttrMustYn().getDate5() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "date5", bcaDto.getContentAttrLogicalAttr().getDate5(), bcaDto.getContentAttrType().getDate5(), bcaDto.getContentAttrMustYn().getDate5(), bcaDto.getContentAttrOrder().getDate5(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getBool1() != null && bcaDto.getContentAttrType().getBool1() != null && bcaDto.getContentAttrMustYn().getBool1() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "bool1", bcaDto.getContentAttrLogicalAttr().getBool1(), bcaDto.getContentAttrType().getBool1(), bcaDto.getContentAttrMustYn().getBool1(), bcaDto.getContentAttrOrder().getBool1(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getBool2() != null && bcaDto.getContentAttrType().getBool2() != null && bcaDto.getContentAttrMustYn().getBool2() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "bool2", bcaDto.getContentAttrLogicalAttr().getBool2(), bcaDto.getContentAttrType().getBool2(), bcaDto.getContentAttrMustYn().getBool2(), bcaDto.getContentAttrOrder().getBool2(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getBool3() != null && bcaDto.getContentAttrType().getBool3() != null && bcaDto.getContentAttrMustYn().getBool3() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "bool3", bcaDto.getContentAttrLogicalAttr().getBool3(), bcaDto.getContentAttrType().getBool3(), bcaDto.getContentAttrMustYn().getBool3(), bcaDto.getContentAttrOrder().getBool3(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getBool4() != null && bcaDto.getContentAttrType().getBool4() != null && bcaDto.getContentAttrMustYn().getBool4() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "bool4", bcaDto.getContentAttrLogicalAttr().getBool4(), bcaDto.getContentAttrType().getBool4(), bcaDto.getContentAttrMustYn().getBool4(), bcaDto.getContentAttrOrder().getBool4(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getBool5() != null && bcaDto.getContentAttrType().getBool5() != null && bcaDto.getContentAttrMustYn().getBool5() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "bool5", bcaDto.getContentAttrLogicalAttr().getBool5(), bcaDto.getContentAttrType().getBool5(), bcaDto.getContentAttrMustYn().getBool5(), bcaDto.getContentAttrOrder().getBool5(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getFloat1() != null && bcaDto.getContentAttrType().getFloat1() != null && bcaDto.getContentAttrMustYn().getFloat1() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "float1", bcaDto.getContentAttrLogicalAttr().getFloat1(), bcaDto.getContentAttrType().getFloat1(), bcaDto.getContentAttrMustYn().getFloat1(), bcaDto.getContentAttrOrder().getFloat1(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }
        if (bcaDto.getContentAttrLogicalAttr().getFloat2() != null && bcaDto.getContentAttrType().getFloat2() != null && bcaDto.getContentAttrMustYn().getFloat2() != null){
            ContentAttrDto contentAttrDto = getContentAttrDtoByBoardAndContentAttrDto(bcaDto.getBoardId(), "float2", bcaDto.getContentAttrLogicalAttr().getFloat2(), bcaDto.getContentAttrType().getFloat2(), bcaDto.getContentAttrMustYn().getFloat2(), bcaDto.getContentAttrOrder().getFloat2(), loginUserId);
            contentAttrMapper.insertContentAttr(contentAttrDto);
        }

        return bcaDto.getBoardNm() + " 게시판이 개설되었습니다.";
    }

    private ContentAttrDto getContentAttrDtoByBoardAndContentAttrDto(Integer boardId, String physicalAttr, String logicalAttr, String attrType, String mustYn, Integer attrOrder, String loginUserId) {
        ContentAttrDto contentAttrDto = new ContentAttrDto();
        contentAttrDto.setBoardId(boardId);
        contentAttrDto.setLogicalAttr(logicalAttr);
        contentAttrDto.setPhysicalAttr(physicalAttr);
        contentAttrDto.setAttrType(attrType);
        contentAttrDto.setMustYn(mustYn);
        contentAttrDto.setAttrOrder(attrOrder);
        contentAttrDto.setRegUserId(loginUserId);
        contentAttrDto.setModfUserId(loginUserId);
        return contentAttrDto;
    }

    public String deleteBoard(String loginUserId, String boardId){
        boardMapper.deleteBoard(boardId, loginUserId);
        return boardId + " 게시판이 삭제되었습니다.";
    }
}
