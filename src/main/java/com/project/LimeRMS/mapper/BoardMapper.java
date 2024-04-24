package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.BoardAndContentAttrDto;
import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import com.project.LimeRMS.dto.BoardInfoDto;

@Mapper
public interface BoardMapper {

    List<BoardInfoDto> findAllBoardInfoList();

    List<Board> findAllBoardList(@Param("authPriority") Integer authPriority);

    Board findOneByBoardId(@Param("boardId") String boardId);

    BoardListDto findOneByContentId(@Param("contentId") Integer contentId);

    void updateBoardSnByBoardId(@Param("boardSn")Integer boardSn, @Param("boardId")Integer boardId, @Param("modfUserId")String modfUserId);

    List<String> findAllBoardManagerByBoardId(@Param("boardId") Integer boardId);

    Integer findBoardModfAuthByContentId(@Param("contentId") Integer contentId);

    String findBoardImgPathByBoardId(@Param("boardId") Integer boardId);

    void updateBoardImgByBoardId(@Param("boardId") Integer boardId, @Param("boardImgPath") String boardImgPath, @Param("modfUserId") String modfUserId);

    @Options(useGeneratedKeys = true, keyProperty = "boardId")
    void insertBoard(BoardAndContentAttrDto bcaDto);

    void deleteBoard(@Param("boardId") String boardId, @Param("modfUserId") String modfUserId);

    Integer findLastBoardSn();
}
