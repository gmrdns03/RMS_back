package com.project.LimeRMS.controller;

import com.project.LimeRMS.service.CommonService;
import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.service.ContentAttrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "ContentAttrController", description = "ContentAttrController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contentAttr")
public class ContentAttrController {
    private final ContentAttrService contentAttrService;
    private final JwtProvider jwtProvider;
    private final CommonService commonService;

    @PostMapping("/add")
    @Operation(
            summary = "컨텐츠 속성 생성",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @ExampleObject(value = "{\"boardId\":\"2\", \"logicalAttr\":\"강사명\", \"physicalAttr\":\"text1\", \"mustYn\":\"N\", \"attrOrder\":\"1\", \"attrType\":\"CD007001\"}")
                    )
            )
    )
    public ResponseEntity<?> addContentAttr(
            @Parameter(hidden = true) @RequestHeader("AccessToken") String token,
            @RequestBody Map<String, String> contentAttrInfo
    ){
        Map<String, Object> resMap = new HashMap<>();
        try{
            String loginUserId = jwtProvider.getUserPk(token);
            String message = contentAttrService.saveContentAttr(loginUserId, contentAttrInfo);
            resMap = commonService.makeReturnData(true, 200, message, true);
        } catch(Exception e){
            resMap = commonService.makeReturnData(false, 500, e.getMessage(), false);
        }
        return ResponseEntity.ok().body(resMap);
    }
}
