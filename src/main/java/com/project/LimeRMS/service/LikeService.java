package com.project.LimeRMS.service;

import com.project.LimeRMS.entity.Content;
import com.project.LimeRMS.mapper.LikeMapper;
import com.project.LimeRMS.security.JwtProvider;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final JwtProvider jwtProvider;
    private final LikeMapper likeMapper;


    public String likes(String likeUserId, Map<String, Integer> content) {
        Integer contentId = content.get("contentId");
        likeMapper.likeContent(Integer.valueOf(likeUserId),contentId);
        return "좋아요가 등록되었습니다.";
    }
}
