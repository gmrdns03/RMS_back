package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.dto.ContentAttrDto;
import com.project.LimeRMS.dto.ContentDtlDto;
import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.entity.Content;
import com.project.LimeRMS.mapper.BoardMapper;
import com.project.LimeRMS.mapper.ContentMapper;
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

    public String getContentDtail(String userId, Integer contentId) {
        // 1. userId로 사용자 권한 조회
        Integer userAuthPriority = userService.getUserAuthPriority(userId);
        // 2. 사용자 권한으로 해당 보드 조회 권한이 있는지 확인
        // 컨텐츠 권한 확인
        BoardListDto boardDto = boardMapper.findViewAuthByContentId(contentId);
        if (userAuthPriority >= boardDto.getViewAuth()) {
            return "";
        }
        // 3. 보드속성 테이블에서 조회 대상 컬럼 확인
        List<ContentAttrDto> contentAttrList = contentMapper.findContentAttrByBoardId(boardDto.getBoardId());
        List<String> pysicalAttrList = new ArrayList<>();
        List<String> logicalAttrList = new ArrayList<>();
        for (ContentAttrDto attr : contentAttrList) {
            pysicalAttrList.add(attr.getPhysicalAttr());
            logicalAttrList.add(attr.getLogicalAttr());
        }
        // 4. 조회 대상 컬럼까지 cotent table에서 컨텐츠 상세 조회
        ContentDtlDto contentDtlDto = contentMapper.findOneByContentId(contentId);
        Map<String, String> freeFieldLabel = new HashMap<>();
        Map<String, String> freeFieldValue = new HashMap<>();



    }
}
