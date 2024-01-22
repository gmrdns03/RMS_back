package com.project.LimeRMS.Content.service;

import com.project.LimeRMS.Common.service.CommonService;
import com.project.LimeRMS.Content.dto.SearchResListDto;
import com.project.LimeRMS.Common.mapper.CommCdMapper;
import com.project.LimeRMS.Content.mapper.SearchMapper;
import com.project.LimeRMS.User.service.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UserService userService;
    private final SearchMapper searchMapper;
    private final CommCdMapper commCdMapper;
    private final CommonService commonService;

    public List<SearchResListDto> getSearchResultList(String keyword, String userId, Integer inputBoardId) throws Exception{
        // 1. user의 권한 확인
        Integer userAuthPriority = userService.getUserAuthPriority(userId);
        // 2. 권한으로 검색 가능한 컨텐츠 리스트 조회
        List<SearchResListDto> searchResListDtoList;
        if (inputBoardId == null) {
            searchResListDtoList =  searchMapper.findAllByKeyword(keyword, userAuthPriority);
        } else {
            searchResListDtoList = searchMapper.findAllBykeywordBoardId(keyword, userAuthPriority,
                inputBoardId);
        }
        Map<String, List<SearchResListDto>> searchResMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (SearchResListDto searchRes : searchResListDtoList) {
            // 날짜 형식 변환
            String modfDtString = searchRes.getModfDt();
            Date modfDtDateType = dateParser.parse(modfDtString);
            String modfDt = formatter.format(modfDtDateType);
            searchRes.setModfDt(modfDt);

            // rentalStat이 비어 있을 경우 디폴트 값 셋팅
            String rentalStat = searchRes.getRentalStat();
            if (rentalStat == null || rentalStat.isEmpty()) {
                rentalStat = "CD001002";
            }
            searchRes.setRentalStat(rentalStat);

            // 대여 상태 이름 set
            String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);
            searchRes.setRentalStatNm(rentalStatNm);

            // 카테고리 set
            Integer cateId = searchRes.getCateId();

            Map<String, Object> cateMap = commonService.getContentHigherachy(cateId);
            searchRes.setSmallCateId((Integer) cateMap.get("smallCateId"));
            searchRes.setMiddleCateId((Integer) cateMap.get("middleCateId"));
            searchRes.setMajorCateId((Integer) cateMap.get("majorCateId"));
            searchRes.setSmallCateNm((String) cateMap.get("smallCateNm"));
            searchRes.setMiddleCateNm((String) cateMap.get("middleCateNm"));
            searchRes.setMajorCateNm((String) cateMap.get("majorCateNm"));
        }

        return searchResListDtoList;
    }
}
