package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void saveComment(String userId, Integer contentId, String comment, Integer score){
        commentMapper.saveComment(userId, contentId, comment, score);
    }

}
