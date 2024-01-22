package com.project.LimeRMS.Content.controller;

import com.project.LimeRMS.Content.dto.SearchResListDto;
import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.Content.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "SearchController", description = "SearchController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final JwtProvider jwtProvider;
    private final SearchService searchService;
    private final CommonService commonService;

    @GetMapping
    @Operation(summary = "전체 검색")
    public ResponseEntity<?> getSearchResultAll(
        @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
        @RequestParam(required = true) String keyword,
        @RequestParam(required = false) Integer boardId
    ) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            String userId = jwtProvider.getUserPk(token);
            List<SearchResListDto> searchRes = searchService.getSearchResultList(keyword, userId, boardId);
            data.put("searchResult", searchRes);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
