package com.project.LimeRMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.project.LimeRMS.dto.LikeListDto;
import java.util.List;

@Mapper
public interface LikeMapper {
    void likeContent(@Param("likeUserId")Integer likeUserId,@Param("contentId")Integer contentId);
    List<LikeListDto> findLikeListByUserId(String userId);

}
