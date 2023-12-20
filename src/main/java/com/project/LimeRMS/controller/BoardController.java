package com.project.LimeRMS.controller;

import com.project.LimeRMS.dto.BoardInfoDto;
import com.project.LimeRMS.mapper.BoardMapper;
import com.project.LimeRMS.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/list")
    @Operation(summary = "보드 목록 반환")
    public List<BoardInfoDto> getBoardInfoList() { return boardService.findAllBoardInfo(); }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoardImg(@PathVariable("boardId") Integer boardId) {

    }
}
