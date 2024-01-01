package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.ContentListDto;
import com.project.LimeRMS.dto.RentalListDto;
import com.project.LimeRMS.entity.BoardManager;
import java.time.LocalDateTime;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RentalMapper {
    List<ContentListDto> findRentalByRentalStat(String rentalStat);
    void updateOverdue();
    List<RentalListDto> findRentalListByUserId(String userId);

    List<ContentListDto> findOverdueRentalByUserId(String userId);

    List<ContentListDto> findReturnRentalByUserId(String userId);

    String findLatestStatByContentId(Integer contentId);

    void rentalContentByContentId(@Param("rentalMap")Map<String, Object> rentalMap);

    Integer findUserRentalCntByUserId(@Param("userId") Integer userId);

    void contentReturn(@Param("userId") Integer userId, @Param("contentId") Integer contentId, @Param("returnDt") LocalDateTime returnDt);

    Integer findExtensionCntByContentId(@Param("contentId")Integer contentId,@Param("rentalUserId")String rentalUserId);


    Integer findExtensionLimitByContentId(@Param("contentId")Integer contentId);

    Integer findRentalPeriod(@Param("contentId")Integer contentId);

    void updateRental(@Param("rentalUserId")String userId, @Param("contentId")Integer contentId, @Param("predReturnDt")LocalDateTime predReturnDt,
        @Param("extensionCnt") Integer extensionCnt);

    List<BoardManager> getBoardMangerAuthPriority(@Param("userId")String userId, @Param("boardId")Integer boardId);

    Integer getBoardId(@Param("contentId")Integer contentId);
}
