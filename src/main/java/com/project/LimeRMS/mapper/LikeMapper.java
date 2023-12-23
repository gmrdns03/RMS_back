package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.LikeListDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    List<LikeListDto> findLikeListByUserId(String userId);
}
