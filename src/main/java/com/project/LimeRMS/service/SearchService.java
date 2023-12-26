package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.SearchResListDto;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.SearchMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Map<String, List<SearchResListDto>> getSearchResultList(String keyword, String userId, Integer inputBoardId) throws Exception{
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

            Integer contentId = searchRes.getContentId();
            String contentNm = searchRes.getContentNm();
            String contentDesc = searchRes.getContentDesc();

            // 날짜 형식 변환
            String modfDtString = searchRes.getModfDt();
            Date modfDtDateType = dateParser.parse(modfDtString);
            String modfDt = formatter.format(modfDtDateType);

            Integer boardId = searchRes.getBoardId();
            String boardNm = searchRes.getBoardNm();
            Integer boardSn = searchRes.getBoardSn();
            String cateNm = searchRes.getCateNm();

            // rentalStat이 비어 있을 경우 디폴트 값 셋팅
            String rentalStat = searchRes.getRentalStat();
            if (rentalStat == null || rentalStat.isEmpty()) {
                rentalStat = "CD001002";
            }
            String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);

            // reservYn이 비어있을 경우 디폴트 값 셋팅
            String reservYn = searchRes.getReservYn();
            if (reservYn == null || reservYn.isEmpty()) {
                reservYn = "N";
            }
            SearchResListDto tempSearchRes = new SearchResListDto(
                contentId, contentNm, contentDesc, modfDt, boardId, boardNm, boardSn,
                cateNm, rentalStat, rentalStatNm, reservYn
            );

            boolean isBoardNm = searchResMap.containsKey(boardNm);
            if (!isBoardNm) {
                searchResMap.put(boardNm, new ArrayList<>());
            }
            searchResMap.get(boardNm).add(tempSearchRes);
        }

        return searchResMap;
    }
}
