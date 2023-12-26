package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.dto.ContentDtlDto;
import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.entity.Content;
import com.project.LimeRMS.mapper.BoardMapper;
import com.project.LimeRMS.mapper.CommCdMapper;
import com.project.LimeRMS.mapper.ContentMapper;
import com.project.LimeRMS.mapper.LikeMapper;
import com.project.LimeRMS.mapper.RentalMapper;
import com.project.LimeRMS.mapper.ReservationMapper;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentMapper contentMapper;
    private final BoardMapper boardMapper;
    private final RentalMapper rentalMapper;
    private final CommCdMapper commCdMapper;
    private final ReservationMapper reservationMapper;
    private final LikeMapper likeMapper;
    private final UserService userService;

    public List<ContentInfoDto> getContentsByBoardId(String boardId) {
        List<Content> contentList = contentMapper.findContentsByBoardId(boardId);
        List<ContentInfoDto> contentInfoDtoList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (Content content: contentList) {
            Integer contentId = content.getContentId();
            Integer cateId = content.getContentCategory().getCateId();
            String cateNm = content.getContentCategory().getCateNm();
            String contentNm = content.getContentNm();
            String contentDesc = content.getContentDesc();
            String noticeYn = content.getNoticeYn();
            String secretYn = content.getSecretYn();
            String location = content.getLocation();
            String regDt =  formatter.format(content.getRegDt());
            String regUserId = content.getRegUserId();
            String modfDt = formatter.format(content.getModfDt());
            String modfUserId = content.getModfUserId();
            ContentInfoDto contentInfoDto = new ContentInfoDto(contentId, cateId, cateNm,
                contentNm, contentDesc, noticeYn, secretYn, location, regDt, regUserId, modfDt, modfUserId);
            contentInfoDtoList.add(contentInfoDto);
        }

        return  contentInfoDtoList;
    }

    public Map<String, Object> getContentDtail(String userId, Integer contentId) throws Exception {
        Map<String, Object> resMap = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // 1. userId로 사용자 권한 조회
        Integer userAuthPriority = userService.getUserAuthPriority(userId);
        // 2. 사용자 권한으로 해당 보드 조회 권한이 있는지 확인
        // 컨텐츠 권한 확인
        BoardListDto boardDto = boardMapper.findViewAuthByContentId(contentId);
        Integer boardViewAuth = boardDto.getViewAuth();
        if (userAuthPriority >= boardViewAuth) {
            throw new IllegalAccessException("해당 페이지에 접근 권한이 없습니다.");
        }
        // 3. 보드속성 테이블에서 조회 대상 컬럼 확인
        List<ContentAttrDto> contentAttrList = contentMapper.findContentAttrByBoardId(boardDto.getBoardId());
        List<String> pysicalAttrArr = new ArrayList<>();
        List<String> logicalAttrArr = new ArrayList<>();
        List<Integer> attrOrderArr = new ArrayList<>();
        for (ContentAttrDto attr : contentAttrList) {
            pysicalAttrArr.add(attr.getPhysicalAttr());
            logicalAttrArr.add(attr.getLogicalAttr());
            attrOrderArr.add(Integer.valueOf(attr.getAttrOrder()));
        }

        // 4. 조회 대상 컬럼까지 cotent table에서 컨텐츠 상세 조회
        Content content = contentMapper.findOneByContentId(contentId);
        Integer contentDtlId = content.getContentId();
        Integer cateId = content.getContentCategory().getCateId();
        String cateNm = content.getContentCategory().getCateNm();
        Integer boardId = content.getBoard().getBoardId();
        String contentNm = content.getContentNm();
        String contentDesc = content.getContentDesc();
        String contentHtml = content.getContentHtml();
        String noticeYn = content.getNoticeYn();
        String secretYn = content.getSecretYn();
        String location = content.getLocation();
        String rentalMessage = content.getRentalMessage();
        String regDt = formatter.format(content.getRegDt());
        Integer regUserId = Integer.valueOf(content.getRegUserId());
        String modfDt = formatter.format(content.getModfDt());
        Integer modfUserId = Integer.valueOf(content.getModfUserId());

        // 컨텐츠 대여 상태 조회
        String rentalStat = rentalMapper.findLatestStatByContentId(contentId);
        if (rentalStat == null || rentalStat.isEmpty()) {
            rentalStat = "CD001002";
        }
        String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);

        // 컨텐츠 예약 상태 조회
        String reservedYn = reservationMapper.findReserveYnByContentId(contentId);
        if (reservedYn == null || reservedYn.isEmpty()) {
            reservedYn = "N";
        }

        // 컨텐츠 좋아요 개수 조회
        Integer likeCnt = likeMapper.countLikeByContentId(contentId);

        ContentDtlDto contentDtlDto = new ContentDtlDto(
            contentDtlId, cateId, cateNm, boardId, contentNm, contentDesc, contentHtml, noticeYn, secretYn,
            location, rentalMessage, regDt, regUserId, modfDt, modfUserId, rentalStat, rentalStatNm, reservedYn, likeCnt
        );

        Map<String, Object> freeFieldMap = new HashMap<>();
        int i = 0;
        for (Integer attrOrder : attrOrderArr) {
            Map<String, String> freeField = new HashMap<>();
            freeField.put("label", logicalAttrArr.get(i));
            freeField.put("value", content.getFreeField(pysicalAttrArr.get(i)));
            freeFieldMap.put(String.valueOf(attrOrder), freeField);
            i++;
        }

        resMap.put("boardDtl", boardDto);
        resMap.put("contentDtl", contentDtlDto);
        resMap.put("contentFreeFields", freeFieldMap);
        return resMap;
    }
}
