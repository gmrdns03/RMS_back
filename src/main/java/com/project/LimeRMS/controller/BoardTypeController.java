package com.project.LimeRMS.controller;

import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.dto.BoardTypeDto;
import com.project.LimeRMS.service.BoardTypeService;
import com.project.LimeRMS.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "BoardTypeController", description = "BoardTypeController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/boardType")
public class BoardTypeController {
    private final BoardTypeService boardTypeService;
    private final CommonService commonService;
    private final JwtProvider jwtProvider;

    @GetMapping("/list")
    @Operation(
            summary = "보드 타입 불러오기"
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    content = @io.swagger.v3.oas.annotations.media.Content(
//                            examples = @ExampleObject(value = "{\"boardTypeNm\":\"일반게시판\", \"boardTypeDesc\":\"일반게시판\"," +
//                                    "\"contentViewType\":\"normal\", \"boardViewType\":\"normal\"")
//                    )
//            )
    )
    public ResponseEntity<?> getBoardTypeList(){
        Map<String, Object> resMap;
        Map<String, Object> data = new HashMap<>();
        try {
            List<BoardTypeDto> boardTypeDtoList = boardTypeService.getBoardTypeList();
            data.put("BoardTypeList", boardTypeDtoList);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e){
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
