package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.CommentDto;
import com.project.LimeRMS.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void saveComment(String userId, Integer contentId, String comment, Integer score){
        commentMapper.saveComment(userId, contentId, comment, score);
    }

    public void modifyComment(String userId, Integer commentId, String comment, Integer score) {
        commentMapper.modifyComment(userId, commentId, comment, score);
    }

    public void deleteComment(String userId, Integer commentId) {
        commentMapper.deleteComment(userId, commentId);
    }

    public List<CommentDto> getCommentList(Integer contentId) {
        return commentMapper.listComment(contentId);
    }
}
