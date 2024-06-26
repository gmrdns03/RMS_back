package com.project.LimeRMS.controller;

import com.project.LimeRMS.Config.security.JwtProvider;
import com.project.LimeRMS.dto.CommCdDto;
import com.project.LimeRMS.dto.CommentDto;
import com.project.LimeRMS.dto.UserInfoDto;
import com.project.LimeRMS.service.CommentService;
import com.project.LimeRMS.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommentController", description = "CommentController API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommonService commonService;
    private final JwtProvider jwtProvider;

    @PostMapping(value = "/list")
    @Operation(summary = "콘텐츠별 댓글 조회",
        description = "콘텐츠별로 작성된 모든 댓글을 조회할 수 있다",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\":\"1\"}"))))
    public ResponseEntity<?> getComment(@RequestBody Map<String, String> contentComment) {
        Map<String, Object> resMap ;
        Map<String, Object> data = new HashMap<>();
        try {
            Integer contentId = Integer.valueOf(contentComment.get("contentId"));
            List<CommentDto> commentDto =  commentService.getCommentList(contentId);
            data.put("CommentInfo", commentDto);
            resMap = commonService.makeReturnData(true, 200, "완료", data);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping
    @Operation(
        summary = "댓글 추가",
        description = "사용자는 댓글을 달 수 있다",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"contentId\":\"10\", \"parentCommentId\":\"10\", \"comment\":\"I love this book\", \"score\":\"10\"}"))))
    public ResponseEntity<?> getCommentInformation(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, String> commentInfo){
        Map<String, Object> resMap;
        try {
            String userId = jwtProvider.getUserPk(token);
            Integer contentId = Integer.valueOf(commentInfo.get("contentId"));
            Integer parentCommentId = commentInfo.get("parentCommentId") != null ? Integer.valueOf(commentInfo.get("parentCommentId")) : null;
            String comment = commentInfo.get("comment");
            Integer score = Integer.valueOf(commentInfo.get("score"));
            commentService.saveComment(userId, contentId, parentCommentId, comment, score);
            String msg = "Posted new comment successfully";
            resMap = commonService.makeReturnData(true, 201, msg, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PutMapping(value = "/delete")
    @Operation(
        summary = "댓글 삭제",
        description = "사용자는 댓글을 삭제할 수 있다",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"commentId\":\"10\"}"))))
    public ResponseEntity<?> deleteComment(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, String> commentInfo){
        Map<String, Object> resMap;
        try {
            String userId = jwtProvider.getUserPk(token);
            Integer commentId = Integer.valueOf(commentInfo.get("commentId"));
            commentService.deleteComment(userId, commentId);
            String msg = "Deleted comment successfully";
            resMap = commonService.makeReturnData(true, 201, msg, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }

    @PostMapping(value = "/modify")
    @Operation(
        summary = "댓글 수정",
        description = "사용자는 댓글을 수정할 수 있다",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @ExampleObject(value = "{\"commentId\":\"28\", \"comment\":\"I want to change my comment\", \"score\":\"10\"}"))))
    public ResponseEntity<?> getModifiedComment(@Parameter(hidden = true) @RequestHeader("AccessToken") String token, @RequestBody Map<String, String> commentInfo){
        Map<String, Object> resMap;
        try {
            String userId = jwtProvider.getUserPk(token);
            Integer commentId = Integer.valueOf(commentInfo.get("commentId"));
            String comment = commentInfo.get("comment");
            Integer score = Integer.valueOf(commentInfo.get("score"));
            commentService.modifyComment(userId, commentId, comment, score);
            String msg = "Modified given comment successfully";
            resMap = commonService.makeReturnData(true, 201, msg, true);
            return ResponseEntity.ok().body(resMap);
        } catch (Exception e) {
            resMap = commonService.makeReturnData(false, 400, e.getMessage(), false);
            return ResponseEntity.ok().body(resMap);
        }
    }
}
