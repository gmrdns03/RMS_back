package com.project.LimeRMS.service;

import com.project.LimeRMS.mapper.LikeMapper;
import java.util.Map;
import com.project.LimeRMS.dto.LikeListDto;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.LikeMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;
    private final CommCdMapper commCdMapper;


    public String likes(String likeUserId, Map<String, Integer> content) {
        Integer contentId = content.get("contentId");
        likeMapper.likeContent(Integer.valueOf(likeUserId), contentId);
        return "좋아요가 등록되었습니다.";
    }

    public String cancelLikes(String likeUserId, Map<String, Integer> content) {
        Integer contentId = content.get("contentId");
        likeMapper.unLike(likeUserId, contentId);
        return "좋아요가 취소되었습니다.";
    }

    public List<LikeListDto> getUserLikeList (String userId) throws Exception {
        List<LikeListDto> likeListDtos = likeMapper.findLikeListByUserId(userId);
        List<LikeListDto> resLikeListDtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateParder = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (LikeListDto likeListDto : likeListDtos) {
            Integer likeId = likeListDto.getLikeId();
            String boardNm = likeListDto.getBoardNm();
            Integer boardId = likeListDto.getBoardId();
            String cateNm = likeListDto.getCateNm();
            Integer contentId = likeListDto.getContentId();
            String contentNm = likeListDto.getContentNm();
            // 날짜 형식 변환
            String regDtString = likeListDto.getRegDt();
            Date regDtDateType = dateParder.parse(regDtString);
            String regDt = formatter.format(regDtDateType);
            String rentalStat = likeListDto.getRentalStat();
            // rentalStat이 비어 있을 경우 디폴트 값 셋팅
            if (rentalStat == null || rentalStat.isEmpty()) {
                rentalStat = "CD001002";
            }
            String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);
            LikeListDto tempLikeListDto = new LikeListDto(likeId, boardNm, boardId, cateNm, contentId, contentNm,
                regDt, rentalStat, rentalStatNm);

            resLikeListDtos.add(tempLikeListDto);
        }

        return resLikeListDtos;
    }
}