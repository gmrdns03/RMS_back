package com.project.LimeRMS.Board.mapper;

import com.project.LimeRMS.Board.dto.BoardListDto;
import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.project.LimeRMS.Board.dto.BoardInfoDto;

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

    void insertBoard(@Param("boardNm") String boardNm, @Param("boardDesc") String boardDesc, @Param("viewAuth") Integer viewAuth, @Param("writeAuth") Integer writeAuth, @Param("boardTypeId") Integer boardTypeId, @Param("contentViewType") String contentViewType, @Param("modfUserId") String modfUserId);
}
