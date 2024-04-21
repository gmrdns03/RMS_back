package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.BoardListDto;
import com.project.LimeRMS.entity.Board;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
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

    void insertBoard(@Param("boardTypeId") Integer boardTypeId, @Param("boardNm") String boardNm, @Param("boardDesc") String boardDesc, @Param("boardStat") String boardStat, @Param("boardSn") Integer boardSn, @Param("rentalPeriod") String rentalPeriod, @Param("extensionLimit") String extensionLimit, @Param("rentalLimit") String rentalLimit, @Param("viewAuth") Integer viewAuth, @Param("writeAuth") Integer writeAuth, @Param("commentAuth") Integer commentAuth, @Param("modifyAuth") Integer modifyAuth, @Param("scoreYn") String scoreYn, @Param("commentYn") String commentYn, @Param("listNumLimit") Integer listNumLimit, @Param("commentImgYn") String commentImgYn, @Param("regUserId") String regUserId);

//    void updateBoard()

    void deleteBoard(@Param("boardId") String boardId, @Param("modfUserId") String modfUserId);

    Integer findLastBoardSn();
}
