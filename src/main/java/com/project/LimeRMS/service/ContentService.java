package com.project.LimeRMS.service;

import com.project.LimeRMS.dto.ContentInfoDto;
import com.project.LimeRMS.mapper.ContentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentMapper contentMapper;
    public List<ContentInfoDto> getContentsByBoardId(String boardId) {
        return  contentMapper.findContentsByBoardId(boardId);
    }
}
