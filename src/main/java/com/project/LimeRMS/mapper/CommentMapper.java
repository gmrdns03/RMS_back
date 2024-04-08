package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.CommentDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

    void saveComment(@Param("userId")String userId, @Param("contentId")Integer contentId, @Param("comment")String comment, @Param("score")Integer score);

    void modifyComment(@Param("userId")String userId, @Param("commentId")Integer commentId, @Param("comment")String comment, @Param("score")Integer score);

    void deleteComment(@Param("userId")String userId, @Param("commentId")Integer commentId);

    List<CommentDto> listComment(@Param("contentId")Integer contentId);
}
