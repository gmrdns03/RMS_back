package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.security.JwtProvider;
import com.project.LimeRMS.service.BoardService;
import com.project.LimeRMS.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "BoardController", description = "BoardController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ContentService contentService;
    private final JwtProvider jwtProvider;

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
            resMap.put("res", true);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("msg", e.getMessage());
            resMap.put("statusCode", 204);
            return ResponseEntity.ok().body(resMap);
        }
    }

    // board에 해당하는 컨텐츠 리스트 반환
    @GetMapping("/{boardId}")
    @Operation(summary = "보드의 컨텐츠 리스트 반환")
    public ResponseEntity<Map<String, Object>> getContentInfoList(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @PathVariable("boardId") String boardId
    ) {
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            Integer loginUserId = Integer.valueOf(jwtProvider.getUserPk(token));
            BoardListDto boardInfo = boardService.getBoardInfo(loginUserId, boardId);
            List<ContentInfoDto> contentInfoList = contentService.getContentsByBoardId(boardId);
            data.put("boardInfo", boardInfo);
            data.put("contentInfoList", contentInfoList);
            resMap.put("res", true);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 204);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }

    // board 대표 이미지 반환
    @GetMapping("/{boardId}/img")
    public ResponseEntity<?> getBoardImg(@PathVariable("boardId") String boardId) {
        Map<String, Object> resMap = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        try {
            File destFile = boardService.getBoardImg(boardId);

            // header 생성
            String mimeType = Files.probeContentType(Paths.get(destFile.getAbsolutePath()));
            if (mimeType == null) {
                mimeType = "octet-steam";
            }
            Resource rs = new UrlResource(destFile.toURI());
            data.put("image", rs);
            resMap.put("res", true);
            resMap.put("statusCode", 200);
            resMap.put("data", data);
            return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; \"")
                .cacheControl(CacheControl.noCache())
                .contentType(MediaType.parseMediaType(mimeType))
                .body(rs);
        } catch (Exception e) {
            resMap.put("res", false);
            resMap.put("statusCode", 404);
            resMap.put("msg", e.getMessage());
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
        Map<String, Object> resMap = new HashMap<>();
        try {
            String loginUserId = jwtProvider.getUserPk(token);
            boardService.saveBoardImg(loginUserId, boardId, multipartFile);
            resMap.put("res", true);
            resMap.put("statusCode", 201);
            resMap.put("msg", "Registered a new board image successfully");
            return ResponseEntity.ok().body(resMap);
        } catch (IllegalAccessException e) {
            resMap.put("res", false);
            resMap.put("statusCode", 403);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        } catch (IOException e) {
            resMap.put("res", false);
            resMap.put("statusCode", 400);
            resMap.put("msg", e.getMessage());
            return ResponseEntity.ok().body(resMap);
        }
    }
}
