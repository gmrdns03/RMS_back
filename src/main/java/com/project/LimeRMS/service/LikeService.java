package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.LikeMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;


    public String likes(String likeUserId, Map<String, Integer> content) {
        Integer contentId = content.get("contentId");
        likeMapper.likeContent(Integer.valueOf(likeUserId),contentId);
        return "좋아요가 등록되었습니다.";
    }


    public String cancelLikes(String likeUserId, Map<String, Integer> content) {
        Integer contentId = content.get("contentId");
        likeMapper.unLike(likeUserId,contentId);
        return "좋아요가 취소되었습니다.";
    }
}
