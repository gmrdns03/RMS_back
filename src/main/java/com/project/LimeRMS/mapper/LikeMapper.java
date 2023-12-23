package com.project.LimeRMS.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

    void likeContent(@Param("likeUserId")Integer likeUserId,@Param("contentId")Integer contentId);
}
