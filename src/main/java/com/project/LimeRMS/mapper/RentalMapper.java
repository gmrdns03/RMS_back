package com.project.LimeRMS.mapper;

import com.project.LimeRMS.dto.ContentListDto;
import com.project.LimeRMS.dto.RentalListDto;
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

    void findRentalByContentId(@Param("rentalUserId")Integer rentalUserId, @Param("contentId")Integer contentId, @Param("modfUserId")String modfUserId);

    ContentListDto findCanceledRentalByUserId(@Param("userId")Integer receiverId, @Param("contentId")Integer contentId);
}
