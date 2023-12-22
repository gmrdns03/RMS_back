package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.BoardInfoDto;
import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.service.BoardService;
import com.project.LimeRMS.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BoardController", description = "BoardController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final ContentService contentService;

    // board 리스트 반환
    @GetMapping("/list")
    @Operation(summary = "보드 목록 반환")
    public List<BoardInfoDto> getBoardInfoList() { return boardService.findAllBoardInfo(); }

    // board에 해당하는 컨텐츠 리스트 반환
    @GetMapping("/{boardId}")
    @Operation(summary = "보드의 컨텐츠 리스트 반환")
    public List<ContentInfoDto> getContentInfoList(@PathVariable("boardId") String boardId) {
        return contentService.getContentsByBoardId(boardId);
    }

    // board 대표 이미지 반환
    @GetMapping("/{boardId}/img")
    public ResponseEntity<?> getBoardImg(@PathVariable("boardId") String boardId) {
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
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
