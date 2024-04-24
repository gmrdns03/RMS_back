package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.BoardAndContentAttrDto;
import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.service.BoardService;
import com.project.LimeRMS.service.CommonService;
import com.project.LimeRMS.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "BoardController", description = "BoardController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ContentService contentService;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;

    @PostMapping("/add")
    @Operation(
            summary = "보드 생성",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"boardTypeId\":\"1\",\"boardNm\":\"보드 이름\",\"boardDesc\":\"보드 설명\",\"boardStat\":\"CD003001\",\"viewAuth\":9,\"writeAuth\":9,\"commentAuth\":9,\"modifyAuth\":9,\"scoreYn\":\"Y\",\"commentYn\":\"Y\",\"listNumLimit\":100,\"commentImgYn\":\"N\"," +
                                    "\"contentAttrLogicalAttr\":{\"text1\":\"강사명\", \"text2\":\"강의 키워드\", \"text3\":\"수준\", \"text4\":\"강좌 링크\"}," +
                                    "\"contentAttrType\":{\"text1\":\"CD007001\", \"text2\":\"CD007001\", \"text3\":\"CD007001\", \"text4\":\"CD007001\"}," +
                                    "\"contentAttrMustYn\":{\"text1\":\"Y\", \"text2\":\"N\", \"text3\":\"Y\", \"text4\":\"N\"}}"))))
    public ResponseEntity<?> addBoard(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
            @RequestBody BoardAndContentAttrDto boardAndContentAttrDto
    ) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            String message = boardService.saveBoard(loginUserId, boardAndContentAttrDto);
            resMap = commonService.makeReturnData(true, 200, message, true);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 500, e.getMessage(), false);
        }
        return ResponseEntity.ok().body(resMap);
    }

    // board 리스트 반환
    @GetMapping("/list")
    @Operation(summary = "보드 목록 반환")
    public ResponseEntity<?> getBoardInfoList(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token
    ) {
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            List<BoardListDto> boardList = boardService.findAllBoardList(userId);
            data.put("boardList", boardList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 204, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping("/remove")
    @Operation(
            summary = "보드 삭제",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"boardId\":\"100\"}"))))
    public ResponseEntity<?> deleteBoard(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
            @RequestBody Map<String, String> boardInfo
    ) {
        Map<String, Object> resMap = new HashMap<>();
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            String boardId = String.valueOf(boardInfo.get("boardId"));
            String message = boardService.deleteBoard(loginUserId, boardId);
            resMap = commonService.makeReturnData(true, 200, message, true);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 500, e.getMessage(), false);
        }
        return ResponseEntity.ok().body(resMap);
    }

    // board에 해당하는 컨텐츠 리스트 반환
    @GetMapping("/{boardId}")
    @Operation(summary = "보드의 컨텐츠 리스트 반환")
    public ResponseEntity<Map<String, Object>> getContentInfoList(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
            @PathVariable("boardId") String boardId
    ) {
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            Integer loginUserId = Integer.valueOf(jwtProvider.getUserPk(token));
            BoardListDto boardInfo = boardService.getBoardInfo(loginUserId, boardId);
            List<ContentInfoDto> contentInfoList = contentService.getContentsByBoardId(boardId);
            Map<String, Object> boardFreeFields = boardService.getBoardFreeField(boardId);
            data.put("boardFreeFields", boardFreeFields);
            data.put("boardInfo", boardInfo);
            data.put("contentInfoList", contentInfoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 204, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    // board 대표 이미지 반환
    @GetMapping("/{boardId}/img")
    public ResponseEntity<?> getBoardImg(@PathVariable("boardId") String boardId) {
        Map<String, Object> resMap;
        try {
            File destFile = boardService.getBoardImg(boardId);

            // header 생성
            String mimeType = Files.probeContentType(Paths.get(destFile.getAbsolutePath()));
            if (mimeType == null) {
                mimeType = "octet-steam";
            }
            Resource rs = new UrlResource(destFile.toURI());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; \"")
                    .cacheControl(CacheControl.noCache())
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(rs);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 404, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping("/{boardId}/save-img")
    @Operation(
            summary = "보드 대표 이미지 저장",
            description = "게시판 수정권한이 있으면서, 게시판 관리자여야 게시판 이미지 수정 가능"
    )
    public ResponseEntity<?> saveBoardImg(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
            @PathVariable("boardId") Integer boardId,
            @RequestPart(value = "file") MultipartFile multipartFile
    ) {
        Map<String, Object> resMap;
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            boardService.saveBoardImg(loginUserId, boardId, multipartFile);
            resMap = commonService.makeReturnData(true, 201, "Registered a new board image successfully", true);
            return ResponseEntity.ok().body(resMap);
        } catch (IllegalAccessException e) {
            resMap = commonService.makeReturnData(false, 403, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        } catch (IOException e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
