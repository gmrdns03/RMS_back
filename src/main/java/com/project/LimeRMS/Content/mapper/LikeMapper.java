package com.project.LimeRMS.Content.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.project.LimeRMS.Content.dto.LikeListDto;
import java.util.List;

@Mapper
public interface LikeMapper {
    void likeContent(@Param("likeUserId")Integer likeUserId,@Param("contentId")Integer contentId);

    void unLike(@Param("likeUserId")String likeUserId,@Param("contentId") Integer contentId);

    Integer findLikeByContentUserId(@Param("likeUserId")String likeUserId,@Param("contentId") Integer contentId);

    List<LikeListDto> findLikeListByUserId(String userId);

    Integer countLikeByContentId(@Param("contentId") Integer contentId);

    Integer findUserLikeIdByUserId(@Param("userId") String userId);

}
