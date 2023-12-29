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
    private final CommonService commonService;

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

    public Map<String, Object> getContentDtail(String loginUserId, Integer contentId) throws NullPointerException, IllegalAccessException, Exception {
        Map<String, Object> data = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        // 1. userId로 사용자 권한 조회
        Integer userAuthPriority = userService.getUserAuthPriority(loginUserId);

        BoardListDto boardDto = boardMapper.findOneByContentId(contentId);
        if (boardDto == null) {
            throw new NullPointerException("요청에 대한 컨텐츠가 존재하지 않습니다. url을 확인해주세요");
        }
        boardDto.setBoardStatNm(commCdMapper.findCdNmByCd(boardDto.getBoardStat()));

        // 2. 사용자 권한으로 해당 보드 조회 권한이 있는지 확인
        Integer boardViewAuth = boardDto.getViewAuth();
        if (userAuthPriority >= boardViewAuth) {
            throw new IllegalAccessException("해당 컨텐츠에 대한 접근 권한이 없습니다.");
        }

        // 3. 조회 대상 컬럼까지 cotent table에서 컨텐츠 상세 조회
        Content content = contentMapper.findOneByContentId(contentId);
        Integer contentDtlId = content.getContentId();
        Integer cateId = content.getContentCategory().getCateId();
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

        // 4. 보드속성 테이블에서 자유필드 컬럼 확인
        List<ContentAttrDto> contentAttrList = contentMapper.findContentAttrByBoardId(boardDto.getBoardId());
        Map<String, Object> freeFieldMap = new HashMap<>();
        for (ContentAttrDto dto : contentAttrList) {
            Map<String, Object> freeField = new HashMap<>();
            freeField.put("label", dto.getLogicalAttr());
            freeField.put("value", content.getFreeField(dto.getPhysicalAttr()));
            freeField.put("order", dto.getAttrOrder());
            String type = commCdMapper.findCdNmByCd(dto.getAttrType());
            freeField.put("type", type);
            freeField.put("required", dto.getMustYn());
            freeFieldMap.put(dto.getAttrOrder(), freeField);
        }

        // 컨텐츠 대여 상태 조회
        String rentalStat = rentalMapper.findLatestStatByContentId(contentId);
        if (rentalStat == null || rentalStat.isEmpty()) {
            rentalStat = "CD001002";
        }
        String rentalStatNm = commCdMapper.findCdNmByCd(rentalStat);

        // 컨텐츠 예약 상태 조회
        String reservedYn = reservationMapper.findReserveYnByContentId(contentId);

        // 컨텐츠 좋아요 개수 조회
        Integer likeCnt = likeMapper.countLikeByContentId(contentId);

        // 컨텐츠 카테고리 조회
        Map<String, Object> cateHigherachy = commonService.getContentHigherachy(cateId);

        ContentDtlDto contentDtlDto = new ContentDtlDto(
            contentDtlId, cateId, boardId, contentNm, contentDesc, contentHtml, noticeYn, secretYn,
            location, rentalMessage, regDt, regUserId, modfDt, modfUserId, rentalStat, rentalStatNm, reservedYn, likeCnt,
            (String) cateHigherachy.get("smallCateNm"),
            (String) cateHigherachy.get("middleCateNm"),
            (String) cateHigherachy.get("majorCateNm"),
            (Integer) cateHigherachy.get("smallCateId"),
            (Integer) cateHigherachy.get("middleCateId"),
            (Integer) cateHigherachy.get("majorCateId")
        );

        // 로그인 한 사용자가 작성자인지 여부 확인
        boolean readOnly = true;
        if (modfUserId.equals(Integer.valueOf(loginUserId))) {
            readOnly = false;
        }

        data.put("readOnly", readOnly);
        data.put("boardInfo", boardDto);
        data.put("contentDtl", contentDtlDto);
        data.put("contentFreeFields", freeFieldMap);
        return data;
    }
}
