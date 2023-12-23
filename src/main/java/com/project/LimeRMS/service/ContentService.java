package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.entity.Content;
import com.project.LimeRMS.mapper.ContentMapper;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentMapper contentMapper;
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
}
